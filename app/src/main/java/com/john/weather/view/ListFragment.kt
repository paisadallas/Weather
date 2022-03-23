package com.john.weather.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.weather.R
import com.john.weather.adapter.ClickAdapterWeather
import com.john.weather.adapter.WeatherAdapter
import com.john.weather.databinding.FragmentListBinding
import com.john.weather.viewmodel.ResultState

class ListFragment : BaseFragment(),ClickAdapterWeather{

    val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }

    val args : ListFragmentArgs by navArgs()

    private lateinit var  weatherAdapter : WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherAdapter = WeatherAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = weatherAdapter
        }

        weatherViewModel.cityForecast.observe(viewLifecycleOwner, ::handleState)
        args.city?.let { weatherViewModel.getForecast(it) }?:"No info"

        return binding.root
    }

    private fun handleState(resultState: ResultState?) {
        when(resultState){
            is ResultState.LOADING ->{
            }
            is ResultState.SUCCESS ->{
                weatherAdapter.update(resultState.results.list)
            }
            is ResultState.ERROR ->{
                Toast.makeText(requireContext(),resultState.error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun checkWeather(temp:String,weather:String,description:String) {

        val data= Array<String>(4){""}
        data[0]= args.city.toString()
        data[1]=temp
        data[2]=weather
        data[3]=description

        val intention = ListFragmentDirections.actionListFragmentToCityFragment(data)
        findNavController().navigate(intention)
    }


}