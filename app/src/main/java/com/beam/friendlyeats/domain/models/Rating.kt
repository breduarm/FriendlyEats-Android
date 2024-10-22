package com.beam.friendlyeats.domain.models

data class Rating(
    val userId: String,
    val userName: String,
    val rating: Double,
    val text: String,
)
