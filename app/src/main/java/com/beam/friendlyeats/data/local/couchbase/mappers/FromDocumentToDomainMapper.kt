package com.beam.friendlyeats.data.local.couchbase.mappers

import com.beam.friendlyeats.data.local.couchbase.documents.RestaurantDocument
import com.beam.friendlyeats.domain.models.Restaurant

fun RestaurantDocument.toDomain() = Restaurant(
    id = id,
    name = name,
    city = city,
    category = category,
    photo = photo,
    price = price,
    numRatings = numRatings,
    avgRating = avgRating,
)
