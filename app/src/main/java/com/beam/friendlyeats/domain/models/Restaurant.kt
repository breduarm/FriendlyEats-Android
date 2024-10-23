package com.beam.friendlyeats.domain.models

data class Restaurant(
    val name: String,
    val city: String,
    val category: String,
    val photo: String,
    val price: Int,
    val numRatings: Int,
    val avgRating: Double,
    val ratings: List<Rating>,
) {

    fun getPriceString(): String = when (price) {
        1 -> "$"
        2 -> "$$"
        3 -> "$$$"
        else -> "$$$"
    }
}
