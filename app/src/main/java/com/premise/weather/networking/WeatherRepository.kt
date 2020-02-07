package com.premise.weather.networking

import com.premise.weather.models.Weather
import com.premise.weather.networking.api.WeatherApi
import retrofit2.Call

object WeatherRepository : BaseRepository<WeatherApi, Long, Weather>(WeatherApi::class.java) {

    fun getWeather(locationId: Long) = fetch(locationId)

    override fun apiCall(api: WeatherApi, param: Long): Call<Weather> {
        return api.getLocations(param)
    }
}