package com.beam.friendlyeats.data.local.firestore.mappers

import com.beam.friendlyeats.data.local.firestore.collections.RatingCollection
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantsGroupCollection
import com.beam.friendlyeats.domain.models.Rating
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.models.RestaurantsGroup

fun RestaurantsGroupCollection.toDomain() = RestaurantsGroup(
    groupName = groupName.orEmpty(),
    restaurantsIds = restaurants.orEmpty(),
    restaurants = emptyList(),
)

fun RestaurantCollection.toDomain() = Restaurant(
    name = name ?: "",
    city = city ?: "",
    category = category ?: "",
    photo = photo ?: "",
    price = price,
    numRatings = numRatings,
    avgRating = avgRating,
)

fun RatingCollection.toDomain() = Rating(
    text = text.orEmpty(),
    rating = rating,
    userId = userId.orEmpty(),
    userName = userName.orEmpty(),
    timestamp = timestamp,
)
