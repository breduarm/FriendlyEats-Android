package com.beam.friendlyeats.data.local.firestore.mappers

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantsGroupCollection
import com.beam.friendlyeats.domain.models.RestaurantsGroup

fun RestaurantsGroupCollection.toDomain() = RestaurantsGroup(
    groupName = groupName.orEmpty(),
    restaurants = emptyList() // restaurants.map { it.toDomain() },
)
