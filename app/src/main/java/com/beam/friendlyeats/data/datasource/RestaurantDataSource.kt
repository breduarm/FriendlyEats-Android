package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.dao.RestaurantDaoFirebaseImpl
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantDataSource {

    suspend fun findAllRestaurants(): List<Restaurant>

    suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant>

    fun findAllRestaurantsFlow(): Flow<List<Restaurant>>

    fun addRandomRestaurants(cities: Array<String>, categories: Array<String>)
}

class RestaurantLocalDataSource : RestaurantDataSource {

    private val restaurantDao = RestaurantDaoFirebaseImpl()

    override suspend fun findAllRestaurants(): List<Restaurant> = restaurantDao.findAllRestaurants()

    override suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant> =
        restaurantDao.findRestaurantsByIds(ids)

    override fun findAllRestaurantsFlow(): Flow<List<Restaurant>> =
        restaurantDao.findAllRestaurantsFlow()

    override fun addRandomRestaurants(cities: Array<String>, categories: Array<String>) {
        restaurantDao.addRandomRestaurants(cities, categories)
    }
}
