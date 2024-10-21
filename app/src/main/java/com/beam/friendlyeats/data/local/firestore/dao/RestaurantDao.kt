package com.beam.friendlyeats.data.local.firestore.dao

import android.util.Log
import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.Restaurant
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface RestaurantDao {

    fun getById(restaurantId: String): Flow<RestaurantCollection?>

    suspend fun findAllRestaurants(): List<Restaurant>

    suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant>

    fun findAllRestaurantsFlow(): Flow<List<Restaurant>>

    fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>)
}

class RestaurantDaoFirebaseImpl : RestaurantDao {

    private val db = initFirestore()
    private val collection = db.collection(RestaurantCollection.COLLECTION_KEY)

    private fun initFirestore(): FirebaseFirestore {
        val firestore = Firebase.firestore
        val settings = firestoreSettings {
            // Use persistent disk cache (default)
            setLocalCacheSettings(persistentCacheSettings {})
        }
        firestore.firestoreSettings = settings
        return firestore
    }

    override fun getById(restaurantId: String): Flow<RestaurantCollection?> = callbackFlow {
        val listener = collection.document(restaurantId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(RestaurantDaoFirebaseImpl::class.java.simpleName, "Listen failed.", error)
                close(error)
                return@addSnapshotListener
            }

            val result = snapshot?.toObject(RestaurantCollection::class.java)
            trySend(result)
        }

        awaitClose { listener.remove() }
    }

    override suspend fun findAllRestaurants(): List<Restaurant> = collection
        .get(Source.CACHE)
        .await()
        .toObjects(RestaurantCollection::class.java)
        .map { it.toDomain() }

    override suspend fun findRestaurantsByIds(ids: List<String>): List<Restaurant> =
        collection
            .whereIn(FieldPath.documentId(), ids)
            .get(Source.CACHE)
            .await()
            .toObjects(RestaurantCollection::class.java)
            .map { it.toDomain() }

    override fun findAllRestaurantsFlow(): Flow<List<Restaurant>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.w(RestaurantDaoFirebaseImpl::class.java.simpleName, "Listen failed.", error)
            }

            val result = snapshot?.toObjects(RestaurantCollection::class.java)?.map {
                it.toDomain()
            }.orEmpty()

            trySend(result).isSuccess
        }

        awaitClose { listener.remove() }
    }

    override fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>) {
        newRestaurants.forEach { newRestaurant ->
            collection.add(newRestaurant)
        }
    }
}
