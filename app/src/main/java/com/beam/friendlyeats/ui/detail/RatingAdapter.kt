package com.beam.friendlyeats.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beam.friendlyeats.domain.models.Rating

class RatingAdapter : ListAdapter<Rating, RatingItemViewHolder>(RatingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RatingItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class RatingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind() {
        // TODO: Bind the data to the view
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
