package com.john.weather.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.john.weather.databinding.WeatherItemBinding
import com.john.weather.model.Forecast
import com.john.weather.model.Main

class WeatherAdapter(
    private val clickAdapterWeather: ClickAdapterWeather,
    private var forecastList: MutableList<Forecast> = mutableListOf()

) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {



    fun update(forecast: List<Forecast>){
        forecastList.clear()
        forecastList.addAll(forecast)
        notifyItemRangeChanged(0,itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
       val holder = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return WeatherHolder(holder)

    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {

        holder.bind(forecastList[position].main)
        holder.itemView.setOnClickListener {
            clickAdapterWeather.checkWeather(kf_to_celsius(forecastList[position].main.temp.toString()),forecastList[position].weather[0].main,forecastList[position].weather[0].description)
                   Log.d("ENTRY_DATA",forecastList[position].weather[0].description.toString())
        }

    }

    private fun kf_to_celsius(kelvin: String): String {

        var kelInt : Double= kelvin.toDouble()
        var result = kelInt -(273.15)
        result *= (1.8)
        result += 32.0
        val solution:Double = String.format("%.2f", result).toDouble()
        return "$solution° F"
    }

    override fun getItemCount(): Int = forecastList.size

    class WeatherHolder(private val binding : WeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(main: Main){
            binding.tvDetails.text = main.temp.toString()

        }
    }
}