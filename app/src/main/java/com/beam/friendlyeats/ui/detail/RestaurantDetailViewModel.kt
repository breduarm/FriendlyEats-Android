package com.beam.friendlyeats.ui.detail

import androidx.lifecycle.ViewModel
import com.beam.friendlyeats.domain.models.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RestaurantDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun onUiReady(restaurantId: String) {
        // TODO Query restaurant
    }

    data class UiState(
        val restaurant: Restaurant? = null,
    )
}
