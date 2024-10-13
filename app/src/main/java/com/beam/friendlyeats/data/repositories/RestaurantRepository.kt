package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantDataSourceLocalDataSource
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

class RestaurantRepository {

    private val dataSource = RestaurantDataSourceLocalDataSource()

    suspend fun getAllRestaurants(): List<Restaurant> = dataSource.findAllRestaurants()

    fun observeAllRestaurants(): Flow<List<Restaurant>> = dataSource.findAllRestaurantsFlow()
}