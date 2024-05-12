package com.beam.friendlyeats.data.local.couchbase.documents

data class RestaurantDocument(
    val id: String,
    val name: String,
    val city: String,
    val category: String,
    val photo: String,
    val price: Int,
    val numRatings: Int,
    val avgRating: Double,
)
