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

class RestaurantsAdapter : ListAdapter<Restaurant, RestaurantsViewHolder>(RestaurantsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RestaurantsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemRestaurantBinding.bind(view)

    fun bind(restaurant: Restaurant): Unit = with(binding) {
        restaurantItemName.text = restaurant.name
        restaurantItemCity.text = restaurant.city
        restaurantItemCategory.text = restaurant.category
        restaurantItemPrice.text = restaurant.getPriceString()
        restaurantItemRating.rating = restaurant.avgRating.toFloat()
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