package com.retivov.testregistry.weather

import io.reactivex.Observable
import retrofit2.http.GET

interface WeatherApi {

    @GET("/data/2.5/weather?q=London,uk&APPID=123a4f7c238e5886510e6607930ecdca")
    fun getWeather():Observable<Weather>

}