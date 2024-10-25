package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.dao.RatingDaoFirebaseImpl
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.Rating
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RatingDataSource {

    fun getByRestaurantId(restaurantId: String): Flow<List<Rating>>
}

class RatingLocalDataSource : RatingDataSource {

    private val ratingDao = RatingDaoFirebaseImpl()

    override fun getByRestaurantId(restaurantId: String): Flow<List<Rating>> =
        ratingDao.getByRestaurantId(restaurantId).map { list ->
            list.map { it.toDomain() }
        }
}
