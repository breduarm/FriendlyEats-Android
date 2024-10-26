package com.beam.friendlyeats.domain.repositories

import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantsGroupRepository {

    fun observeAllRestaurantsInRestaurantsGroup(): Flow<List<Restaurant>>
}
