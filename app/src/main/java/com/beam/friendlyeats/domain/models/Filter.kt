package com.beam.friendlyeats.domain.models

import android.content.Context
import android.text.TextUtils
import com.beam.friendlyeats.R
import com.google.firebase.firestore.Query

class Filter {

    var category: String? = null
    var city: String? = null
    var price = -1
    var sortBy: String? = null
    var sortDirection: Query.Direction = Query.Direction.DESCENDING

    fun hasCategory(): Boolean {
        return !TextUtils.isEmpty(category)
    }

    fun hasCity(): Boolean {
        return !TextUtils.isEmpty(city)
    }

    fun hasPrice(): Boolean {
        return price > 0
    }

    fun hasSortBy(): Boolean {
        return !TextUtils.isEmpty(sortBy)
    }

    fun getSearchDescription(context: Context): String {
        val desc = StringBuilder()

        if (category == null && city == null) {
            desc.append("<b>")
            desc.append(context.getString(R.string.all_restaurants))
            desc.append("</b>")
        }

        if (category != null) {
            desc.append("<b>")
            desc.append(category)
            desc.append("</b>")
        }

        if (category != null && city != null) {
            desc.append(" in ")
        }

        if (city != null) {
            desc.append("<b>")
            desc.append(city)
            desc.append("</b>")
        }

        if (price > 0) {
            desc.append(" for ")
            desc.append("<b>")
            desc.append(Restaurant.getPriceString(price))
            desc.append("</b>")
        }

        return desc.toString()
    }

    fun getOrderDescription(context: Context): String {
        return when (sortBy) {
            Restaurant.FIELD_PRICE -> context.getString(R.string.sorted_by_price)
            Restaurant.FIELD_POPULARITY -> context.getString(R.string.sorted_by_popularity)
            else -> context.getString(R.string.sorted_by_rating)
        }
    }

    companion object {

        val default: Filter
            get() {
                val filters = Filter()
                filters.sortBy = Restaurant.FIELD_AVG_RATING
                filters.sortDirection = Query.Direction.DESCENDING

                return filters
            }
    }
}
