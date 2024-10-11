package com.beam.friendlyeats.data.local.firestore.dao

import com.beam.friendlyeats.data.local.firestore.collections.RestaurantCollection
import com.beam.friendlyeats.data.local.firestore.mappers.toDomain
import com.beam.friendlyeats.domain.models.Restaurant
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface RestaurantDao {

    suspend fun findAllRestaurants(): List<Restaurant>
}

class RestaurantDaoFirebaseImpl : RestaurantDao {

    private val db = Firebase.firestore
    private val collection = db.collection(RestaurantCollection.COLLECTION_KEY)

    override suspend fun findAllRestaurants(): List<Restaurant> = collection
        .get()
        .await()
        .toObjects(RestaurantCollection::class.java)
        .map { it.toDomain() }
}