package com.beam.friendlyeats.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beam.friendlyeats.data.repositories.RestaurantRepositoryImpl
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.models.Filter
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state

    private val restaurantRepository = RestaurantRepositoryImpl()

    var isSigningIn: Boolean = false
    var filters: Filter = Filter.default

    fun onUiReady() {
        viewModelScope.launch {
            restaurantRepository
                .observeAllRestaurants()
                .flowOn(Dispatchers.IO)
                .collect { restaurants ->
                    _state.value = _state.value.copy(restaurants = restaurants)
                }
        }
    }

    fun onSignOut(context: Context) {
        AuthUI.getInstance().signOut(context)
    }

    fun onAddRandomItems(cities: Array<String>, categories: Array<String>) {
        restaurantRepository.addRandomRestaurants(cities, categories)
    }

    fun filterRestaurants() {
        viewModelScope.launch {
            val filteredRestaurants = restaurantRepository.getFilteredRestaurants(filters)
            _state.value = _state.value.copy(restaurants = filteredRestaurants)
        }
    }

    data class UiState(
        val restaurants: List<Restaurant> = emptyList(),
    )
}
