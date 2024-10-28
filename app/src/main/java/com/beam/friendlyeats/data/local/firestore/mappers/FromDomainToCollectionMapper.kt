package com.beam.friendlyeats.data.local.firestore.mappers

import com.beam.friendlyeats.data.local.firestore.collections.RatingCollection
import com.beam.friendlyeats.domain.models.Rating

fun Rating.toCollection() = RatingCollection().apply {
    userId = this@toCollection.userId
    userName = this@toCollection.userName
    rating = this@toCollection.rating
    text = this@toCollection.text
}
