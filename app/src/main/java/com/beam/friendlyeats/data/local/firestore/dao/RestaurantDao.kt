package com.beam.friendlyeats.data.local.firestore.dao

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

interface RestaurantDao {

    fun getById(restaurantId: String): Flow<RestaurantCollection?>

    suspend fun findAllRestaurants(): List<RestaurantCollection>

    suspend fun findRestaurantsByIds(ids: List<String>): List<RestaurantCollection>

    fun findAllRestaurantsFlow(): Flow<List<RestaurantCollection>>

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

    override fun getById(restaurantId: String): Flow<RestaurantCollection?> =
        collection.document(restaurantId).dataObjects()

    override suspend fun findAllRestaurants(): List<RestaurantCollection> = collection
        .get(Source.CACHE)
        .await()
        .toObjects(RestaurantCollection::class.java)

    override suspend fun findRestaurantsByIds(ids: List<String>): List<RestaurantCollection> =
        collection
            .whereIn(FieldPath.documentId(), ids)
            .get(Source.CACHE)
            .await()
            .toObjects(RestaurantCollection::class.java)

    override fun findAllRestaurantsFlow(): Flow<List<RestaurantCollection>> = collection.dataObjects()

    override fun addRandomRestaurants(newRestaurants: List<RestaurantCollection>) {
        newRestaurants.forEach { newRestaurant ->
            collection.add(newRestaurant)
        }
    }
}
