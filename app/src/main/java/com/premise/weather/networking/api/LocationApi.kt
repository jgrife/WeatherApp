package com.premise.weather.networking.api

import com.premise.weather.models.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("location/search/")
    fun getLocations(@Query("query") location: String): Call<List<Location>>

    @GET("location/search/")
    fun getLocationsFromLatLong(@Query("lattlong") latAndLong: String): Call<List<Location>>
}