package com.premise.weather.networking.api

import com.premise.weather.models.PostalCodeLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostalCodeApi {

    @GET("https://api.zippopotam.us/us/{postal_code}")
    fun getLocations(@Path("postal_code") postalCode: String): Call<PostalCodeLocation>
}