package com.beam.friendlyeats.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beam.friendlyeats.data.repositories.RatingRepository
import com.beam.friendlyeats.data.repositories.RestaurantRepository
import com.beam.friendlyeats.domain.models.Rating
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RestaurantDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private val restaurantRepository = RestaurantRepository()
    private val ratingRepository = RatingRepository()

    fun onUiReady(restaurantId: String) {
        viewModelScope.launch {
            val restaurantFlow = restaurantRepository.getRestaurantById(restaurantId)
            restaurantFlow.flowOn(Dispatchers.IO).collect { restaurant ->
                _state.value = _state.value.copy(restaurant = restaurant)
            }
        }

        viewModelScope.launch {
            val ratingsFlow = ratingRepository.getRatingsByRestaurantId(restaurantId)
            ratingsFlow.flowOn(Dispatchers.IO).collect { ratings ->
                _state.value = _state.value.copy(ratings = ratings)
            }
        }
    }

    data class UiState(
        val restaurant: Restaurant? = null,
        val ratings: List<Rating> = emptyList(),
    )
}
