package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantDataSourceLocalDataSource
import com.beam.friendlyeats.domain.models.Restaurant

class RestaurantRepository {

    private val dataSource = RestaurantDataSourceLocalDataSource()

    suspend fun findAllRestaurants(): List<Restaurant> = dataSource.findAllRestaurants()
}