package com.john.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
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

        holder.bind(forecastList[position])
        holder.itemView.setOnClickListener {
            clickAdapterWeather.checkWeather()
        }

    }

    override fun getItemCount(): Int = forecastList.size

    class WeatherHolder(private val binding : WeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast){
//            binding.tvDetails.text = main.temp.toString()
            binding.tvDetails.text = forecast.dt.toString()
        }
    }
}