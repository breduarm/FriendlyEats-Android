package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantLocalDataSource
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.domain.models.Filter
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.repositories.RestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantRepositoryImpl : RestaurantRepository {

    private val restaurantsDataSource = RestaurantLocalDataSource()

    override fun getRestaurantById(restaurantId: String): Flow<Restaurant> =
        restaurantsDataSource.getById(restaurantId)

    override suspend fun getAllRestaurants(): List<Restaurant> =
        restaurantsDataSource.getAllRestaurants()

    override fun observeAllRestaurants(): Flow<List<Restaurant>> =
        restaurantsDataSource.getAllRestaurantsFlow()

    override fun addRandomRestaurants(cities: Array<String>, categories: Array<String>) {
        val randomRestaurants: MutableList<RestaurantCollection> = mutableListOf()
        for (i in 0..RANDOM_RESTAURANTS_LIMIT) {
            // Create random restaurant / ratings
             randomRestaurants.add(RestaurantCollection.getRandom(cities, categories))
        }
        
        restaurantsDataSource.addRandomRestaurants(randomRestaurants)
    }

    override suspend fun getFilteredRestaurants(filters: Filter): List<Restaurant> =
        restaurantsDataSource.getFilteredRestaurants(filters, FILTERING_QUERY_LIMIT)

    companion object {
        const val RANDOM_RESTAURANTS_LIMIT = 3
        const val FILTERING_QUERY_LIMIT = 50L
    }
}
