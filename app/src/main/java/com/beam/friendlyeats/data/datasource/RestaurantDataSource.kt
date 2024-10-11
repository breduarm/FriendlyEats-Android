package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.dao.RestaurantDaoFirebaseImpl
import com.beam.friendlyeats.domain.models.Restaurant

interface RestaurantDataSource {

    suspend fun findAllRestaurants(): List<Restaurant>
}

class RestaurantDataSourceLocalDataSource : RestaurantDataSource {

    private val restaurantDao = RestaurantDaoFirebaseImpl()

    override suspend fun findAllRestaurants(): List<Restaurant> = restaurantDao.findAllRestaurants()
}
