package com.beam.friendlyeats.data.local.firestore.collections

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class RestaurantsGroupCollection(
    var groupName: String? = null,
    var restaurants: List<String>? = null,
) {

    companion object {

        const val COLLECTION_KEY = "restaurantsGroup"
    }
}
