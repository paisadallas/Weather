package com.john.weather.view

import androidx.fragment.app.Fragment
import com.john.weather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment:Fragment() {
    protected val weatherViewModel: WeatherViewModel by viewModel()
}