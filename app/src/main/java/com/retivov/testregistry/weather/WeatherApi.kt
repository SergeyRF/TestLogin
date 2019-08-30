package com.retivov.testregistry.weather

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") q: String = "Moscow",
                   @Query("units") units: String = "metric",
                   @Query("APPID") APPID: String = "123a4f7c238e5886510e6607930ecdca"): Observable<Weather>

}