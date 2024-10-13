package com.beam.friendlyeats.data.local.firestore.dao

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.Restaurant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface RestaurantDao {

    suspend fun findAllRestaurants(): List<Restaurant>
}

class RestaurantDaoFirebaseImpl : RestaurantDao {

    private val db = initFirestore()
    private val collection = db.collection(RestaurantCollection.COLLECTION_KEY)

    override suspend fun findAllRestaurants(): List<Restaurant> = collection
        .get(Source.CACHE)
        .await()
        .toObjects(RestaurantCollection::class.java)
        .map { it.toDomain() }

    private fun initFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore
        val settings = firestoreSettings {
            // Use persistent disk cache (default)
            setLocalCacheSettings(persistentCacheSettings {})
        }
        firestore.firestoreSettings = settings
        return firestore
    }
}