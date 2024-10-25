package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RatingLocalDataSource
import com.beam.friendlyeats.domain.models.Rating
import kotlinx.coroutines.flow.Flow

class RatingRepository {

    private val ratingDataSource = RatingLocalDataSource()

    fun getRatingsByRestaurantId(restaurantId: String): Flow<List<Rating>> =
        ratingDataSource.getByRestaurantId(restaurantId)
}
