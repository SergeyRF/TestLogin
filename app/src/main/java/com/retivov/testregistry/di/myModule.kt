package com.retivov.testregistry.di

import com.retivov.testregistry.screens.RegistryViewModel
import com.retivov.testregistry.weather.WeatherApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val myModel = module {

    viewModel { RegistryViewModel(get()) }

    single { getWeatherApi() }

}

private fun getWeatherApi(): WeatherApi {
    val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://samples.openweathermap.org")

            .build()
    return retrofit.create(WeatherApi::class.java)
}
