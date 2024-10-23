package com.beam.friendlyeats.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beam.friendlyeats.databinding.ItemRatingBinding
import com.beam.friendlyeats.domain.models.Rating

class RatingAdapter : ListAdapter<Rating, RatingItemViewHolder>(RatingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingItemViewHolder {
        val view = ItemRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RatingItemViewHolder(
    private val binding: ItemRatingBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(rating: Rating?): Unit = with(binding) {
        if (rating == null) return@with

        ratingItemText.text = rating.text
        ratingItemName.text = rating.userName
        ratingItemRating.rating = rating.rating.toFloat()
    }
}

object RatingDiffCallback : DiffUtil.ItemCallback<Rating>() {

    override fun areItemsTheSame(oldItem: Rating, newItem: Rating): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Rating, newItem: Rating): Boolean {
        return oldItem == newItem
    }
}
