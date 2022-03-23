package com.john.weather

import android.app.Application
import android.util.Log
import com.john.weather.adapter.ClickAdapterWeather
import com.john.weather.di.networkModule
import com.john.weather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(listOf(networkModule, viewModelModule))
        }
    }

}