package com.beam.friendlyeats.data.repositories

import com.beam.friendlyeats.data.datasource.RatingLocalDataSource
import com.beam.friendlyeats.domain.models.Rating
import com.beam.friendlyeats.domain.repositories.RatingRepository
import kotlinx.coroutines.flow.Flow

class RatingRepositoryImpl : RatingRepository {

    private val ratingDataSource = RatingLocalDataSource()

    override fun getRatingsByRestaurantId(restaurantId: String): Flow<List<Rating>> =
        ratingDataSource.getByRestaurantId(restaurantId)
}
