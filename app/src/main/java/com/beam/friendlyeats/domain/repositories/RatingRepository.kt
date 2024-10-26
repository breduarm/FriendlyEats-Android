package com.beam.friendlyeats.domain.repositories

import com.beam.friendlyeats.domain.models.Rating
import kotlinx.coroutines.flow.Flow

interface RatingRepository {

    fun getRatingsByRestaurantId(restaurantId: String): Flow<List<Rating>>
}
