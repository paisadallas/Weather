package com.john.weather.viewmodel

import com.john.weather.model.Result


sealed class ResultState {
    object LOADING : ResultState()
    class SUCCESS(val results: Result) : ResultState()
    class ERROR(val error: Throwable) : ResultState()
}