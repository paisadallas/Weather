package com.john.weather.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.john.weather.R
import com.john.weather.databinding.FragmentSearchBinding
import com.john.weather.viewmodel.ResultState

/**
 * This fragment check the avaliability of the city
 * if the city exist we can access to the data
 * otherwise we'll receive a message city not found
 */
class SearchFragment : BaseFragment() {

    private val binding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        binding.btSearch.setOnClickListener {
            weatherViewModel.cityForecast.observe(viewLifecycleOwner,::handleState)
            weatherViewModel.getForecast(binding.etSearch.text.toString())
        }
        return binding.root
    }

    private fun handleState(resultState: ResultState?) {
        var found:Boolean = false
        var city = ""
        when(resultState){
            is ResultState.LOADING ->{
                found =false
                binding.tvCityFound.text ="Searching ..."
            }
            is ResultState.SUCCESS ->{

                found=true
                binding.tvCityFound.text = binding.etSearch.text.toString()
                binding.tvWeatherFound.text = "Found"
            }
            is ResultState.ERROR ->{
                found =false
                binding.tvCityFound.text= "City no found :("

            }
        }
        binding.cvCity.setOnClickListener {
            if (found){

                city = binding.etSearch.text.toString()

                val intention = SearchFragmentDirections.actionSearchFragmentToListFragment(city)
                findNavController().navigate(intention)
            }else{

            }

        }
    }

}