package com.beam.friendlyeats.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.beam.friendlyeats.R
import com.beam.friendlyeats.databinding.FragmentRestaurantDetailBinding
import com.beam.friendlyeats.domain.models.Restaurant
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class RestaurantDetailFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailBinding
    private val viewModel: RestaurantDetailViewModel by viewModels()

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

        val restaurantId = RestaurantDetailFragmentArgs.fromBundle(requireArguments()).keyRestaurantId

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.restaurant?.let { onRestaurantLoaded(it) }
                }
            }
        }

        viewModel.onUiReady(restaurantId)
    }

    private fun onRestaurantLoaded(restaurant: Restaurant) = with(binding) {
        restaurantName.text = restaurant.name
        restaurantCity.text = restaurant.city
        restaurantPrice.text = restaurant.getPriceString()
        restaurantCategory.text = restaurant.category
        restaurantRating.rating = restaurant.avgRating.toFloat()
        restaurantNumRatings.text = getString(R.string.fmt_num_ratings, restaurant.numRatings)

        Glide.with(restaurantImage.context)
            .load(restaurant.photo)
            .into(restaurantImage)
    }
}
