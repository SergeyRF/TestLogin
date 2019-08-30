package com.retivov.testregistry.di

import com.retivov.testregistry.screens.LoginViewModel
import com.retivov.testregistry.weather.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val myModel = module {

    viewModel { LoginViewModel(get()) }

    single { getClient() }
    single { getWeatherApi(get()) }

}

private fun getWeatherApi(client: OkHttpClient): WeatherApi {
    val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org")
            .client(client)
            .build()
    return retrofit.create(WeatherApi::class.java)
}

private fun getClient() =
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
