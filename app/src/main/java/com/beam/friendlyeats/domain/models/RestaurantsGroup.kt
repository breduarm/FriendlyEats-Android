package com.beam.friendlyeats.domain.models

data class RestaurantsGroup(
    val groupName: String,
    val restaurantsIds: List<String>,
    val restaurants: List<Restaurant>,
)
