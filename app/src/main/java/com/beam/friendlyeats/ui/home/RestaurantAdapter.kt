package com.beam.friendlyeats.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.beam.friendlyeats.R
import com.beam.friendlyeats.databinding.ItemRestaurantBinding
import com.beam.friendlyeats.domain.models.Restaurant
import com.beam.friendlyeats.domain.models.Restaurant.Companion.getPriceString
import com.bumptech.glide.Glide

class RestaurantsAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Restaurant, RestaurantsViewHolder>(RestaurantsDiffCallback) {

    interface OnItemClickListener {
        fun onItemClick(restaurant: Restaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

class RestaurantsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemRestaurantBinding.bind(view)

    fun bind(
        restaurant: Restaurant,
        listener: RestaurantsAdapter.OnItemClickListener
    ): Unit = with(binding) {
        val resources = binding.root.resources
        val numRatings: Int = restaurant.numRatings

        root.setOnClickListener {
            listener.onItemClick(restaurant)
        }
        restaurantItemName.text = restaurant.name
        restaurantItemCity.text = restaurant.city
        restaurantItemCategory.text = restaurant.category
        restaurantItemPrice.text = getPriceString(restaurant.price)
        binding.restaurantItemNumRatings.text =
            resources.getString(R.string.fmt_num_ratings, numRatings)
        restaurantItemRating.rating = restaurant.avgRating.toFloat()

        Glide.with(restaurantItemImage.context)
            .load(restaurant.photo)
            .into(restaurantItemImage)
    }
}

object RestaurantsDiffCallback : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}
