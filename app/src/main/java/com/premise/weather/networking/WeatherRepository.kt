package com.premise.weather.networking

import com.premise.weather.models.Weather
import com.premise.weather.networking.api.WeatherApi
import retrofit2.Call

object WeatherRepository : BaseRepository<WeatherApi, Int, Weather>(WeatherApi::class.java) {

    fun getWeather(locationId: Int) = fetch(locationId)

    override fun apiCall(api: WeatherApi, param: Int): Call<Weather> {
        return api.getLocations(param)
    }
}