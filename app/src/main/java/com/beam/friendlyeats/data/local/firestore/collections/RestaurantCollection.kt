package com.beam.friendlyeats.data.local.firestore.collections

import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.Arrays
import java.util.Locale
import java.util.Random

/**
 * Restaurant POJO.
 */
@IgnoreExtraProperties
data class RestaurantCollection(
    var name: String? = null,
    var city: String? = null,
    var category: String? = null,
    var photo: String? = null,
    var price: Int = 0,
    var numRatings: Int = 0,
    var avgRating: Double = 0.toDouble(),
) {

    companion object {

        const val COLLECTION_KEY = "restaurants"
        const val FIELD_CITY = "city"
        const val FIELD_CATEGORY = "category"
        const val FIELD_PRICE = "price"
        const val FIELD_POPULARITY = "numRatings"
        const val FIELD_AVG_RATING = "avgRating"
        private const val RESTAURANT_URL_FMT = "https://storage.googleapis.com/firestorequickstarts.appspot.com/food_%d.png"
        private const val MAX_IMAGE_NUM = 22

        private val NAME_FIRST_WORDS = arrayOf(
            "Foo", "Bar", "Baz", "Qux", "Fire", "Sam's", "World Famous", "Google", "The Best")

        private val NAME_SECOND_WORDS = arrayOf(
            "Restaurant", "Cafe", "Spot", "Eatin' Place", "Eatery", "Drive Thru", "Diner")

        /**
         * Create a random Restaurant POJO.
         */
        fun getRandom(cities: Array<String>, categories: Array<String>): RestaurantCollection {
            val newRestaurant = RestaurantCollection()
            val random = Random()

            // Cities (first element is 'Any')
            var processedCities = cities
            processedCities = Arrays.copyOfRange(processedCities, 1, processedCities.size)

            // Categories (first element is 'Any')
            var processedCategories = categories
            processedCategories = Arrays.copyOfRange(processedCategories, 1, processedCategories.size)

            val prices = intArrayOf(1, 2, 3)

            newRestaurant.name = getRandomName(random)
            newRestaurant.city = getRandomString(processedCities, random)
            newRestaurant.category = getRandomString(processedCategories, random)
            newRestaurant.photo = getRandomImageUrl(random)
            newRestaurant.price = getRandomInt(prices, random)
            newRestaurant.numRatings = random.nextInt(20)

            // Note: average rating intentionally not set

            return newRestaurant
        }

        /**
         * Get a random image.
         */
        private fun getRandomImageUrl(random: Random): String {
            // Integer between 1 and MAX_IMAGE_NUM (inclusive)
            val id = random.nextInt(MAX_IMAGE_NUM) + 1

            return String.format(Locale.getDefault(), RESTAURANT_URL_FMT, id)
        }

        private fun getRandomName(random: Random): String {
            return (getRandomString(NAME_FIRST_WORDS, random) + " " +
                    getRandomString(NAME_SECOND_WORDS, random))
        }

        private fun getRandomString(array: Array<String>, random: Random): String {
            val ind = random.nextInt(array.size)
            return array[ind]
        }

        private fun getRandomInt(array: IntArray, random: Random): Int {
            val ind = random.nextInt(array.size)
            return array[ind]
        }
    }
}
