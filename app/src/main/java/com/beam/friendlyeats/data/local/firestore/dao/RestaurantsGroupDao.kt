package com.beam.friendlyeats.data.local.firestore.dao

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantsGroupCollection
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

interface RestaurantsGroupDao {

    fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroupCollection>>
}

class RestaurantsGroupDaoFirebaseImpl : RestaurantsGroupDao {

    private val db = initFirestore()
    private val collection = db.collection(RestaurantsGroupCollection.COLLECTION_KEY)

    private fun initFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore
        val settings = firestoreSettings {
            // Use persistent disk cache (default)
            setLocalCacheSettings(persistentCacheSettings {})
        }
        firestore.firestoreSettings = settings
        return firestore
    }

    override fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroupCollection>> =
        collection.dataObjects()
}
