package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantLocalDataSource
import com.beam.friendlyeats.data.datasource.RestaurantsGroupLocalDataSource
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestaurantRepository {

    private val restaurantsDataSource = RestaurantLocalDataSource()

    suspend fun getAllRestaurants(): List<Restaurant> =
        restaurantsDataSource.findAllRestaurants()

    fun observeAllRestaurants(): Flow<List<Restaurant>> =
        restaurantsDataSource.findAllRestaurantsFlow()

    fun addRandomRestaurants() {
        restaurantsDataSource.addRandomRestaurants()
    }
}
