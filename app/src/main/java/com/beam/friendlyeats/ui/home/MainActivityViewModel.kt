package com.beam.friendlyeats.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beam.friendlyeats.data.repositories.RestaurantRepository
import com.beam.friendlyeats.domain.models.Restaurant
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state

    private val restaurantRepository = RestaurantRepository()

    var isSigningIn: Boolean = false

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

    data class UiState(
        val restaurants: List<Restaurant> = emptyList(),
    )
}
