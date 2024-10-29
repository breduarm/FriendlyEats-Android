package com.beam.friendlyeats.data.datasource

import com.beam.friendlyeats.data.local.firestore.dao.RestaurantsGroupDaoFirebaseImpl
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.RestaurantsGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RestaurantsGroupDataSource {

    fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroup>>
}

class RestaurantsGroupLocalDataSource : RestaurantsGroupDataSource {

    private val restaurantsGroupDao = RestaurantsGroupDaoFirebaseImpl()

    override fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroup>> =
        restaurantsGroupDao.findAllRestaurantsGroupFlow().map { list ->
            list.map { it.toDomain() }
        }
}
