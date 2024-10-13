package com.beam.friendlyeats.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beam.friendlyeats.data.repositories.RestaurantRepository
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state

    private val restaurantRepository = RestaurantRepository()

    var isSigningIn: Boolean = false

    fun onUiReady() {
        viewModelScope.launch {
            restaurantRepository.observeAllRestaurants().collect { restaurants ->
                _state.value = _state.value.copy(restaurants = restaurants)
            }
        }
    }

    data class UiState(
        val restaurants: List<Restaurant> = emptyList(),
    )
}