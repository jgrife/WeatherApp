package com.premise.weather.networking.api

import com.premise.weather.models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("location/{id}")
    fun getLocations(@Path("id") locationId: Long): Call<Weather>
}