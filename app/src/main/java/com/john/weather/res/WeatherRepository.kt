package com.john.weather.res


import com.john.weather.model.Result
import retrofit2.Response

class WeatherRepositoryImp (
    private val weatherServiceApi: ServiceApi
        ): WeatherRepository{
    override suspend fun getCityForecast(city: String): Response<Result> {
        return weatherServiceApi.getForecast(city = city)
    }

}

interface WeatherRepository{
    suspend fun getCityForecast(city : String): Response<Result>
}