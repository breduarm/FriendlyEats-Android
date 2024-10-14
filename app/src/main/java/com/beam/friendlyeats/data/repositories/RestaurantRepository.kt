package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantLocalDataSource
import com.beam.friendlyeats.data.datasource.RestaurantsGroupLocalDataSource
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestaurantRepository {

    private val restaurantsDataSource = RestaurantLocalDataSource()
    private val restaurantsGroupDataSource = RestaurantsGroupLocalDataSource()

    suspend fun getAllRestaurants(): List<Restaurant> = restaurantsDataSource.findAllRestaurants()

    fun observeAllRestaurants(): Flow<List<Restaurant>> = restaurantsDataSource.findAllRestaurantsFlow()

    fun observeAllRestaurantsInRestaurantsGroup(): Flow<List<Restaurant>> =
        restaurantsGroupDataSource.findAllRestaurantsGroupFlow().map { groups ->
            val firstGroup = groups.firstOrNull()
            val restaurantsIds = firstGroup?.restaurantsIds.orEmpty()
            restaurantsDataSource.findRestaurantsByIds(restaurantsIds)
        }
}