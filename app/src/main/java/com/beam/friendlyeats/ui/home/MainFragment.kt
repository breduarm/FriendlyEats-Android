package com.beam.friendlyeats.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.beam.friendlyeats.R
import com.beam.friendlyeats.databinding.FragmentMainBinding
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.models.Filter
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainFragment : Fragment(), MenuProvider, RestaurantsAdapter.OnItemClickListener,
    FilterDialogFragment.FilterListener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainActivityViewModel by activityViewModels()

    private lateinit var adapter: RestaurantsAdapter
    private lateinit var filterDialog: FilterDialogFragment
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result -> this.onSignInResult(result) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RestaurantsAdapter(listener = this)
        binding.recyclerRestaurants.adapter = adapter
        filterDialog = FilterDialogFragment()
        binding.filterBar.setOnClickListener { onFilterClicked() }
        binding.buttonClearFilter.setOnClickListener { onClearFilterClicked() }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(
            provider = this,
            owner = viewLifecycleOwner,
            state = Lifecycle.State.RESUMED
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    displayEmptyState(shouldShow = state.restaurants.isEmpty())
                    adapter.submitList(state.restaurants)
                }
            }
        }

        viewModel.onUiReady()
    }

    override fun onStart() {
        super.onStart()

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn()
            return
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.menu_sign_out -> {
            viewModel.onSignOut(requireContext())
            startSignIn()
            true
        }

        R.id.menu_add_items -> {
            val cities = resources.getStringArray(R.array.cities)
            val categories = resources.getStringArray(R.array.categories)
            viewModel.onAddRandomItems(cities, categories)
            true
        }

        else -> false
    }

    override fun onItemClick(restaurant: Restaurant) {
        goToRestaurantDetail(restaurantId = restaurant.id)
    }

    override fun onFilter(filters: Filter) {
        val firestore = Firebase.firestore

        // Construct query basic query
        var query: Query = firestore.collection("restaurants")

        // Category (equality filter)
        if (filters.hasCategory()) {
            query = query.whereEqualTo(Restaurant.FIELD_CATEGORY, filters.category)
        }

        // City (equality filter)
        if (filters.hasCity()) {
            query = query.whereEqualTo(Restaurant.FIELD_CITY, filters.city)
        }

        // Price (equality filter)
        if (filters.hasPrice()) {
            query = query.whereEqualTo(Restaurant.FIELD_PRICE, filters.price)
        }

        // Sort by (orderBy with direction)
        if (filters.hasSortBy()) {
            query = query.orderBy(filters.sortBy.toString(), filters.sortDirection)
        }

        // Limit items
        query = query.limit(50.toLong())

        // Update the query
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val restaurants = task.result?.toObjects(Restaurant::class.java)
                adapter.submitList(restaurants)
                displayEmptyState(shouldShow = restaurants.isNullOrEmpty())
            } else {
                displayEmptyState(shouldShow = true)
            }
        }

        // Set header
        binding.textCurrentSearch.text = HtmlCompat.fromHtml(
            filters.getSearchDescription(requireContext()),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.textCurrentSortBy.text = filters.getOrderDescription(requireContext())

        // Save filters
        viewModel.filters = filters
    }

    private fun goToRestaurantDetail(restaurantId: String) {
        // Go to the details page for the selected restaurant
        val action = MainFragmentDirections
            .actionMainFragmentToRestaurantDetailFragment(restaurantId)

        findNavController().navigate(action)
    }

    private fun shouldStartSignIn(): Boolean =
        !viewModel.isSigningIn && Firebase.auth.currentUser == null

    private fun startSignIn() {
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(intent)
        viewModel.isSigningIn = true
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        viewModel.isSigningIn = false

        if (result.resultCode != Activity.RESULT_OK) {
            when {
                // User pressed the back button.
                response == null -> {
                    requireActivity().finish()
                }
                // A network error occurred.
                response.error != null && response.error!!.errorCode == ErrorCodes.NO_NETWORK -> {
                    showSignInErrorDialog(R.string.message_no_network)
                }
                // Unknown error.
                else -> {
                    showSignInErrorDialog(R.string.message_unknown)
                }
            }
        }
    }

    private fun showSignInErrorDialog(@StringRes message: Int) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_sign_in_error)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.option_retry) { _, _ -> startSignIn() }
            .setNegativeButton(R.string.option_exit) { _, _ -> requireActivity().finish() }.create()

        dialog.show()
    }

    private fun onFilterClicked() {
        filterDialog.show(parentFragmentManager, FilterDialogFragment::class.java.simpleName)
    }

    private fun onClearFilterClicked() {
        filterDialog.resetFilters()

        onFilter(Filter.default)
    }

    private fun displayEmptyState(shouldShow: Boolean) = with(binding) {
        recyclerRestaurants.isVisible = !shouldShow
        viewEmpty.isVisible = shouldShow
    }
}
