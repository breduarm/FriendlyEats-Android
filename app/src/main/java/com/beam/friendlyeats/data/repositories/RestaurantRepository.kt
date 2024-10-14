package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantLocalDataSource
import com.beam.friendlyeats.data.datasource.RestaurantsGroupLocalDataSource
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestaurantRepository {

    private val dataSource = RestaurantLocalDataSource()
    private val restaurantsGroupDataSource = RestaurantsGroupLocalDataSource()

    suspend fun getAllRestaurants(): List<Restaurant> = dataSource.findAllRestaurants()

    fun observeAllRestaurants(): Flow<List<Restaurant>> = dataSource.findAllRestaurantsFlow()

    fun observeAllRestaurantsInRestaurantsGroup(): Flow<List<Restaurant>> =
        restaurantsGroupDataSource.findAllRestaurantsGroupFlow().map {
            it.flatMap { group -> group.restaurants }
        }
}