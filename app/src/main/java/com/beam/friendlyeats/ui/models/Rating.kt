package com.beam.friendlyeats.ui.models

import android.text.TextUtils
import com.google.firebase.auth.FirebaseUser
import java.util.Date

/**
 * Model POJO for a rating.
 */
data class Rating(
    var userId: String? = null,
    var userName: String? = null,
    var rating: Double = 0.toDouble(),
    var text: String? = null,
    var timestamp: Date? = null
) {

    constructor(user: FirebaseUser, rating: Double, text: String) : this() {
        this.userId = user.uid
        this.userName = user.displayName
        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.email
        }

        this.rating = rating
        this.text = text
    }
}
