package com.beam.friendlyeats.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.beam.friendlyeats.databinding.DialogRatingBinding
import com.beam.friendlyeats.ui.models.Rating
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Dialog Fragment containing rating form.
 */
class RatingDialogFragment : DialogFragment() {

    private var _binding: DialogRatingBinding? = null
    private val binding get() = _binding!!
    private var ratingListener: RatingListener? = null

    internal interface RatingListener {

        fun onRating(rating: Rating)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (parentFragment is RatingListener) {
            ratingListener = parentFragment as RatingListener
        } else {
            // No opt
        }
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
            val rating = Rating(
                user = it,
                rating = binding.restaurantFormRating.rating.toDouble(),
                text = binding.restaurantFormText.text.toString()
            )

            ratingListener?.onRating(rating)
        }

        dismiss()
    }

    private fun onCancelClicked() {
        dismiss()
    }

    companion object {

        const val TAG = "RatingDialog"
    }
}
