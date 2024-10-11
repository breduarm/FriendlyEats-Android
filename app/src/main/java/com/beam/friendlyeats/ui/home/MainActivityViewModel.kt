package com.beam.friendlyeats.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beam.friendlyeats.data.repositories.RestaurantRepository
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val restaurantRepository = RestaurantRepository()

    var isSigningIn: Boolean = false

    init {
        viewModelScope.launch {
            val restaurants = findAllRestaurants()
            println("==== Restaurants: $restaurants")
        }
    }

    private suspend fun findAllRestaurants() = restaurantRepository.findAllRestaurants()


}