package com.beam.friendlyeats.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.beam.friendlyeats.databinding.DialogRatingBinding
import com.beam.friendlyeats.domain.models.Rating
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Dialog Fragment containing rating form.
 */
class RatingDialogFragment(private val listener: RatingListener) : DialogFragment() {

    private var _binding: DialogRatingBinding? = null
    private val binding get() = _binding!!

    interface RatingListener {

        fun onRating(newRating: Rating)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRatingBinding.inflate(inflater, container, false)

        with(binding) {
            restaurantFormButton.setOnClickListener { onSubmitClicked() }
            restaurantFormCancel.setOnClickListener { onCancelClicked() }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSubmitClicked() {
        val user = Firebase.auth.currentUser
        user?.let {
            val ratingValue = binding.restaurantFormRating.rating.toDouble()
            val ratingText = binding.restaurantFormText.text.toString()

            val rating = Rating(
                userId = user.uid,
                userName = user.displayName,
                rating = ratingValue,
                text = ratingText,
            )

            listener.onRating(rating)
        }

        dismiss()
    }

    private fun onCancelClicked() {
        dismiss()
    }
}
