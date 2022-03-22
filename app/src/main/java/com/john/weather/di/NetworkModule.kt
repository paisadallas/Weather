package com.john.weather.di

import com.john.weather.res.ServiceApi
import com.john.weather.res.WeatherRepository
import com.john.weather.res.WeatherRepositoryImp
import com.john.weather.viewmodel.WeatherViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun providesLoggingInterceptor()= HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideMoshi() = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .build()

    fun provideServiceApi(okHttpClient:OkHttpClient, moshi: Moshi) =
        Retrofit.Builder()
            .baseUrl(ServiceApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(ServiceApi::class.java)

    single { provideMoshi() }
    single { providesLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideServiceApi(get(),get()) }
}

val viewModelModule = module {
    fun providesWeatherRepo(weatherApi: ServiceApi):WeatherRepository =
        WeatherRepositoryImp(weatherApi)

    single { providesWeatherRepo(get()) }
    viewModel { WeatherViewModel(get()) }
}