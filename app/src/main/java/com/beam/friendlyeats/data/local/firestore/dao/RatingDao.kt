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

    fun addTransactional(restaurantId: String, newRating: RatingCollection)
}

class RatingDaoFirebaseImpl : RatingDao {

    private val db = initFirestore()
    private val restaurantCollRef = db.collection(RestaurantCollection.COLLECTION_KEY)

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
        restaurantCollRef
            .document(restaurantId)
            .collection(RatingCollection.COLLECTION_KEY)
            .dataObjects()

    /**
     * Adds a new rating to a restaurant and updates the fields (`numRatings` and `avgRating`)
     * using a Firestore transaction to ensure consistency.
     *
     * ## Why use a transaction?
     * This approach was chosen instead of retrieving all ratings to recalculate the restaurant
     * for the following reasons:
     *
     * - **Atomic consistency**: Transactions ensure that both the restaurant's fields and
     *   the new rating are written together atomically. This prevents race conditions that
     *   could occur if multiple users rate the same restaurant at the same time.
     *
     * - **Efficiency**: Instead of reading the entire sub collection of ratings, only the
     *   current state of the restaurant is read and updated, minimizing read operations
     *   and network usage.
     *
     * - **Scalability**: This method performs better as the number of ratings grows,
     *   avoiding performance degradation.
     *
     * @param restaurantId The ID of the restaurant to add the rating to.
     * @param newRating The rating object to be added.
     * @throws Exception if the restaurant document does not exist or the transaction fails.
     */
    override fun addTransactional(restaurantId: String, newRating: RatingCollection) {

        val restaurantRef = restaurantCollRef.document(restaurantId)
        val ratingRef = restaurantRef.collection(RatingCollection.COLLECTION_KEY).document()

        db.runTransaction { transaction ->

            val restaurant = transaction
                .get(restaurantRef)
                .toObject(RestaurantCollection::class.java)
                ?: throw Exception("Restaurant not found at ${restaurantRef.path}")

            // Compute new number of ratings
            val newNumRatings = restaurant.numRatings + 1

            // Compute new average rating
            val oldRatingTotal = restaurant.avgRating * restaurant.numRatings
            val newAvgRating = (oldRatingTotal + newRating.rating) / newNumRatings

            // Set new restaurant info
            restaurant.numRatings = newNumRatings
            restaurant.avgRating = newAvgRating

            // Commit to Firestore
            transaction.set(restaurantRef, restaurant)
            transaction.set(ratingRef, newRating)
        }
    }
}
