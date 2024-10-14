package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.dao.RestaurantsGroupDaoFirebaseImpl
import com.beam.friendlyeats.domain.models.RestaurantsGroup
import kotlinx.coroutines.flow.Flow

interface RestaurantsGroupDataSource {

    fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroup>>
}

class RestaurantsGroupLocalDataSource : RestaurantsGroupDataSource {

    private val restaurantsGroupDao = RestaurantsGroupDaoFirebaseImpl()

    override fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroup>> =
        restaurantsGroupDao.findAllRestaurantsGroupFlow()

}