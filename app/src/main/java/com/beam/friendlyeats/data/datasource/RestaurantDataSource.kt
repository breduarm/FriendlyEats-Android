package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.data.local.firestore.dao.RestaurantDaoFirebaseImpl
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.Filter
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

interface RestaurantDataSource {

    fun getById(restaurantId: String): Flow<Restaurant?>

    suspend fun findAllRestaurants(): List<Restaurant>

    suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant>

    fun findAllRestaurantsFlow(): Flow<List<Restaurant>>

    fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>)

    suspend fun getFilteredRestaurants(filters: Filter, limit: Long): List<Restaurant>
}

class RestaurantLocalDataSource : RestaurantDataSource {

    private val restaurantDao = RestaurantDaoFirebaseImpl()

    override fun getById(restaurantId: String): Flow<Restaurant> =
        restaurantDao.getById(restaurantId).mapNotNull { it?.toDomain() }

    override suspend fun findAllRestaurants(): List<Restaurant> =
        restaurantDao.findAllRestaurants().map { it.toDomain() }

    override suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant> =
        restaurantDao.findRestaurantsByIds(ids).map { it.toDomain() }

    override fun findAllRestaurantsFlow(): Flow<List<Restaurant>> =
        restaurantDao.findAllRestaurantsFlow().map { list ->
            list.map { it.toDomain() }
        }

    override fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>) {
        restaurantDao.addRandomRestaurants(newRestaurants)
    }

    override suspend fun getFilteredRestaurants(filters: Filter, limit: Long): List<Restaurant> =
        restaurantDao.getFiltered(filters, limit).map { it.toDomain() }
}
