package com.premise.weather.networking

import com.premise.weather.models.PostalCodeLocation
import com.premise.weather.networking.api.PostalCodeApi
import retrofit2.Call

object PostalCodeRepository : BaseRepository<PostalCodeApi, String, PostalCodeLocation>(PostalCodeApi::class.java) {

    fun getLocations(postalCode: String) = fetch(postalCode)

    override fun apiCall(api: PostalCodeApi, param: String): Call<PostalCodeLocation> {
        return api.getLocations(param)
    }
}