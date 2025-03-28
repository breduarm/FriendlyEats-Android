package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RestaurantLocalDataSource
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.repositories.RestaurantRepository
import kotlinx.coroutines.flow.Flow

class RestaurantRepositoryImpl : RestaurantRepository {

    private val restaurantsDataSource = RestaurantLocalDataSource()

    override fun getRestaurantById(restaurantId: String): Flow<Restaurant> =
        restaurantsDataSource.getById(restaurantId)

    override suspend fun getAllRestaurants(): List<Restaurant> =
        restaurantsDataSource.findAllRestaurants()

    override fun observeAllRestaurants(): Flow<List<Restaurant>> =
        restaurantsDataSource.findAllRestaurantsFlow()

    override fun addRandomRestaurants(cities: Array<String>, categories: Array<String>) {
        val randomRestaurants: MutableList<RestaurantCollection> = mutableListOf()
        for (i in 0..RANDOM_RESTAURANTS_LIMIT) {
            // Create random restaurant / ratings
             randomRestaurants.add(RestaurantCollection.getRandom(cities, categories))
        }
        
        restaurantsDataSource.addRandomRestaurants(randomRestaurants)
    }

    companion object {
        const val RANDOM_RESTAURANTS_LIMIT = 3
    }
}
