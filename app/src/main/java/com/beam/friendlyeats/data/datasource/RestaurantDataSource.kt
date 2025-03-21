package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.data.local.firestore.dao.RestaurantDaoFirebaseImpl
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantDataSource {

    suspend fun findAllRestaurants(): List<Restaurant>

    suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant>

    fun findAllRestaurantsFlow(): Flow<List<Restaurant>>

    fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>)
}

class RestaurantLocalDataSource : RestaurantDataSource {

    private val restaurantDao = RestaurantDaoFirebaseImpl()

    override suspend fun findAllRestaurants(): List<Restaurant> = restaurantDao.findAllRestaurants()

    override suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant> =
        restaurantDao.findRestaurantsByIds(ids)

    override fun findAllRestaurantsFlow(): Flow<List<Restaurant>> =
        restaurantDao.findAllRestaurantsFlow()

    override fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>) {
        restaurantDao.addRandomRestaurants(newRestaurants)
    }
}
