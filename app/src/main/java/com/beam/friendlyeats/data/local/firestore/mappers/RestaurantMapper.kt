package com.beam.friendlyeats.data.local.firestore.mappers

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.domain.models.Restaurant

fun RestaurantCollection.toDomain() = Restaurant(
    name = name ?: "",
    city = city ?: "",
    category = category ?: "",
    photo = photo ?: "",
    price = price,
    numRatings = numRatings,
    avgRating = avgRating,
)