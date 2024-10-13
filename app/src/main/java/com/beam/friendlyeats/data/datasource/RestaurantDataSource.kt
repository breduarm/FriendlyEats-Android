package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.dao.RestaurantDaoFirebaseImpl
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantDataSource {

    suspend fun findAllRestaurants(): List<Restaurant>

    fun findAllRestaurantsFlow(): Flow<List<Restaurant>>
}

class RestaurantDataSourceLocalDataSource : RestaurantDataSource {

    private val restaurantDao = RestaurantDaoFirebaseImpl()

    override suspend fun findAllRestaurants(): List<Restaurant> = restaurantDao.findAllRestaurants()

    override fun findAllRestaurantsFlow(): Flow<List<Restaurant>> =
        restaurantDao.findAllRestaurantsFlow()
}
