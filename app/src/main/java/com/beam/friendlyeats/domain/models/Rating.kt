package com.beam.friendlyeats.domain.models

import java.util.Date

data class Rating(
    val userId: String,
    val userName: String?,
    val rating: Double,
    val text: String,
    val timestamp: Date? = null,
)
