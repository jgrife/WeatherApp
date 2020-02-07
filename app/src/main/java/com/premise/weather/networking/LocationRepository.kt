package com.premise.weather.networking

import com.premise.weather.models.Location
import com.premise.weather.networking.LocationRepository.LocationParams
import com.premise.weather.networking.api.LocationApi
import retrofit2.Call

object LocationRepository : BaseRepository<LocationApi, LocationParams, List<Location>>(LocationApi::class.java) {

    data class LocationParams(val query: String? = null, val latitude: String? = null, val longitude: String? = null)

    fun getLocations(location: String) = fetch(LocationParams(query = location))
    fun getLocations(latitude: String, longitude: String) = fetch(LocationParams(latitude = latitude, longitude = longitude))

    override fun apiCall(api: LocationApi, param: LocationParams): Call<List<Location>> {
        return if (param.query != null) {
            api.getLocations(param.query)
        } else {
            api.getLocationsFromLatLong("${param.latitude},${param.longitude}")
        }
    }
}