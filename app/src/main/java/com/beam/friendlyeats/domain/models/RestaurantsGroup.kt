package com.beam.friendlyeats.domain.models

data class RestaurantsGroup(
    val groupName: String,
    val restaurants: List<Restaurant>,
)
