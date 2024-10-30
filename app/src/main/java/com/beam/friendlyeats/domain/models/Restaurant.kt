package com.beam.friendlyeats.domain.models

data class Restaurant(
    val id: String,
    val name: String,
    val city: String,
    val category: String,
    val photo: String,
    val price: Int,
    val numRatings: Int,
    val avgRating: Double,
) {

    companion object {

        const val FIELD_PRICE = "price"
        const val FIELD_POPULARITY = "numRatings"
        const val FIELD_AVG_RATING = "avgRating"

        fun getPriceString(price: Int): String = when (price) {
            1 -> "$"
            2 -> "$$"
            3 -> "$$$"
            else -> "$$$"
        }
    }
}
