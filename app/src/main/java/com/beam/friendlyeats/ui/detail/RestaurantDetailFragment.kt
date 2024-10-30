package com.beam.friendlyeats.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.beam.friendlyeats.R
import com.beam.friendlyeats.databinding.FragmentRestaurantDetailBinding
import com.beam.friendlyeats.domain.models.Rating
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.models.Restaurant.Companion.getPriceString
import com.beam.friendlyeats.ui.detail.RatingDialogFragment.RatingListener
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class RestaurantDetailFragment : Fragment(), RatingListener {

    private lateinit var binding: FragmentRestaurantDetailBinding
    private val viewModel: RestaurantDetailViewModel by viewModels()

    private lateinit var restaurantId: String
    private lateinit var ratingDialog: RatingDialogFragment
    private lateinit var ratingAdapter: RatingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantId = RestaurantDetailFragmentArgs.fromBundle(requireArguments()).keyRestaurantId

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.restaurant?.let { onRestaurantLoaded(it) }
                    displayRatingEmptyState(shouldShow = state.ratings.isEmpty())
                    ratingAdapter.submitList(state.ratings)
                }
            }
        }

        initializeUI()
        viewModel.onUiReady(restaurantId)
    }

    override fun onRating(newRating: Rating) {
        viewModel.addRating(restaurantId, newRating)
    }

    private fun initializeUI() = with(binding) {
        ratingDialog = RatingDialogFragment(listener = this@RestaurantDetailFragment)
        ratingAdapter = RatingAdapter()
        recyclerRatings.adapter = ratingAdapter
        restaurantButtonBack.setOnClickListener { onBackArrowClicked() }
        fabShowRatingDialog.setOnClickListener { onAddRatingClicked() }
    }

    private fun onRestaurantLoaded(restaurant: Restaurant) = with(binding) {
        restaurantName.text = restaurant.name
        restaurantCity.text = restaurant.city
        restaurantPrice.text = getPriceString(restaurant.price)
        restaurantCategory.text = restaurant.category
        restaurantRating.rating = restaurant.avgRating.toFloat()
        restaurantNumRatings.text = getString(R.string.fmt_num_ratings, restaurant.numRatings)

        Glide.with(restaurantImage.context)
            .load(restaurant.photo)
            .into(restaurantImage)
    }

    private fun onAddRatingClicked() {
        ratingDialog.show(parentFragmentManager, RatingDialogFragment::class.java.simpleName)
    }

    private fun displayRatingEmptyState(shouldShow: Boolean): Unit = with(binding) {
        viewEmptyRatings.isVisible = shouldShow
        recyclerRatings.isVisible = !shouldShow
    }

    private fun onBackArrowClicked() {
        requireActivity().onBackPressed()
    }
}
