package com.beam.friendlyeats.domain.repositories

import com.beam.friendlyeats.domain.models.Filter
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {

    fun getRestaurantById(restaurantId: String): Flow<Restaurant>

    suspend fun getAllRestaurants(): List<Restaurant>

    fun observeAllRestaurants(): Flow<List<Restaurant>>

    fun addRandomRestaurants(cities: Array<String>, categories: Array<String>)

    suspend fun getFilteredRestaurants(filters: Filter): List<Restaurant>
}
