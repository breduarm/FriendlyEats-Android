package com.beam.friendlyeats.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beam.friendlyeats.databinding.FragmentRestaurantDetailBinding

class RestaurantDetailFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}
