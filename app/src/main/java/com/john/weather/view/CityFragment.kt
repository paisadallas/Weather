package com.john.weather.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.john.weather.R
import com.john.weather.databinding.FragmentCityBinding

class CityFragment : Fragment() {

    val binding by lazy {
        FragmentCityBinding.inflate(layoutInflater)
    }

     val args : CityFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tvCity.text = args.data?.get(0) ?: "No info"
        binding.tvWeather.text = args.data?.get(1) ?: "No info"
        binding.tvPrediction.text = args.data?.get(2) ?: "No info"
        binding.tvDescription.text=args.data?.get(3) ?: "No info"

        return binding.root
    }

}