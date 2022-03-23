package com.john.weather.res

import com.john.weather.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET(FORECAST_PATH)
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appId") apiKey: String = API_KEY
    ): Response<Result>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        private const val FORECAST_PATH = "data/2.5/forecast"

        private const val API_KEY = "cc01ca8b30e0e67f466a01e32cc58f01"
    }
}