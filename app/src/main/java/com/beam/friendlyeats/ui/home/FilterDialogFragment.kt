package com.beam.friendlyeats.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.beam.friendlyeats.R
import com.beam.friendlyeats.databinding.DialogFilterBinding
import com.beam.friendlyeats.domain.models.Filter
import com.beam.friendlyeats.domain.models.Restaurant
import com.google.firebase.firestore.Query

class FilterDialogFragment(private val listener: FilterListener) : DialogFragment() {

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding!!

    private val selectedCategory: String?
        get() {
            val selected = binding.spinnerCategory.selectedItem as String
            return if (getString(R.string.value_any_category) == selected) {
                null
            } else {
                selected
            }
        }

    private val selectedCity: String?
        get() {
            val selected = binding.spinnerCity.selectedItem as String
            return if (getString(R.string.value_any_city) == selected) {
                null
            } else {
                selected
            }
        }

    private val selectedPrice: Int
        get() {
            val selected = binding.spinnerPrice.selectedItem as String
            return when (selected) {
                getString(R.string.price_1) -> 1
                getString(R.string.price_2) -> 2
                getString(R.string.price_3) -> 3
                else -> -1
            }
        }

    private val selectedSortBy: String?
        get() {
            val selected = binding.spinnerSort.selectedItem as String
            if (getString(R.string.sort_by_rating) == selected) {
                return Restaurant.FIELD_AVG_RATING
            }
            if (getString(R.string.sort_by_price) == selected) {
                return Restaurant.FIELD_PRICE
            }
            return if (getString(R.string.sort_by_popularity) == selected) {
                Restaurant.FIELD_POPULARITY
            } else {
                null
            }
        }

    private val sortDirection: Query.Direction
        get() {
            val selected = binding.spinnerSort.selectedItem as String
            if (getString(R.string.sort_by_rating) == selected) {
                return Query.Direction.DESCENDING
            }
            if (getString(R.string.sort_by_price) == selected) {
                return Query.Direction.ASCENDING
            }
            return if (getString(R.string.sort_by_popularity) == selected) {
                Query.Direction.DESCENDING
            } else {
                Query.Direction.DESCENDING
            }
        }

    private val filters: Filter
        get() {
            val filters = Filter()

            filters.category = selectedCategory
            filters.city = selectedCity
            filters.price = selectedPrice
            filters.sortBy = selectedSortBy
            filters.sortDirection = sortDirection

            return filters
        }

    interface FilterListener {

        fun onFilter(newFilters: Filter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterBinding.inflate(inflater, container, false)

        binding.buttonSearch.setOnClickListener { onSearchClicked() }
        binding.buttonCancel.setOnClickListener { onCancelClicked() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun onSearchClicked() {
        listener.onFilter(filters)
        dismiss()
    }

    private fun onCancelClicked() {
        dismiss()
    }

    fun resetFilters() {
        _binding?.let {
            it.spinnerCategory.setSelection(0)
            it.spinnerCity.setSelection(0)
            it.spinnerPrice.setSelection(0)
            it.spinnerSort.setSelection(0)
        }
    }
}
