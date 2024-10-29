package com.beam.friendlyeats.data.local.firestore.dao

import com.beam.friendlyeats.data.local.firestore.collections.RatingCollection
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

interface RatingDao {

    fun getByRestaurantId(restaurantId: String): Flow<List<RatingCollection>>

    fun add(restaurantId: String, newRating: RatingCollection)
}

class RatingDaoFirebaseImpl : RatingDao {

    private val db = initFirestore()
    private val restaurantRef = db.collection(RestaurantCollection.COLLECTION_KEY)

    private fun initFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore
        val settings = firestoreSettings {
            // Use persistent disk cache (default)
            setLocalCacheSettings(persistentCacheSettings {})
        }
        firestore.firestoreSettings = settings
        return firestore
    }

    override fun getByRestaurantId(restaurantId: String): Flow<List<RatingCollection>> =
        restaurantRef
            .document(restaurantId)
            .collection(RatingCollection.COLLECTION_KEY)
            .dataObjects()

    override fun add(restaurantId: String, newRating: RatingCollection) {
        restaurantRef
            .document(restaurantId)
            .collection(RatingCollection.COLLECTION_KEY)
            .add(newRating)
    }
}
