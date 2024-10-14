package com.beam.friendlyeats.data.local.firestore.dao

import android.util.Log
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantsGroupCollection
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.RestaurantsGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface RestaurantsGroupDao {

    fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroup>>
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

    override fun findAllRestaurantsGroupFlow(): Flow<List<RestaurantsGroup>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(RestaurantsGroupDaoFirebaseImpl::class.java.simpleName, "Listen failed.", error)
                return@addSnapshotListener
            }
            val result = snapshot?.toObjects(RestaurantsGroupCollection::class.java)?.map {
                it.toDomain()
            }.orEmpty()
            trySend(result).isSuccess
        }
        
        awaitClose { listener.remove() }
    }
}
