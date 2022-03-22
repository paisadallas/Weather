package com.john.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john.weather.res.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _cityForecast: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val cityForecast: LiveData<ResultState> get() = _cityForecast

    fun getForecast(city: String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = weatherRepository.getCityForecast(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) {
                            _cityForecast.value = ResultState.SUCCESS(it)
                            Log.d("RESULT", ResultState.SUCCESS(it).toString())
                        }
                    } ?: throw Exception("Response error")
                } else {
                    throw Exception("No success")
                }
            } catch (e: Exception) {
                _cityForecast.postValue(ResultState.ERROR(e))
            }
        }
    }
}