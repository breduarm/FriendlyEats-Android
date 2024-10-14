package com.beam.friendlyeats.data.local.firestore.mappers

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantsGroupCollection
import com.beam.friendlyeats.domain.models.RestaurantsGroup

fun RestaurantsGroupCollection.toDomain() = RestaurantsGroup(
    groupName = groupName.orEmpty(),
    restaurantsIds = restaurants.orEmpty(),
    restaurants = emptyList(),
)
