package com.beam.friendlyeats.domain.models

data class Restaurant(
    val name: String,
    val city: String,
    val category: String,
    val photo: String,
    val price: Int,
    val numRatings: Int,
    val avgRating: Double,
)
