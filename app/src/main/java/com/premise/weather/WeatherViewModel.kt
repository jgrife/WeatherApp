package com.premise.weather

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.premise.weather.extensions.getExactMatch
import com.premise.weather.extensions.isExactMatch
import com.premise.weather.livedata.Result
import com.premise.weather.livedata.Status
import com.premise.weather.livedata.transformData
import com.premise.weather.models.Location
import com.premise.weather.models.Weather
import com.premise.weather.models.getExactMatch
import com.premise.weather.networking.LocationRepository
import com.premise.weather.networking.PostalCodeRepository
import com.premise.weather.networking.WeatherRepository

class WeatherViewModel : ViewModel() {

    private val _forecastData = MediatorLiveData<Result<Weather>>()
    val forecastData: LiveData<Result<Weather>> = _forecastData
    val locationData: LiveData<List<Location>> = Transformations.map(LocationRepository.data) {
        if (it.data.isExactMatch()) emptyList() else it.data
    }

    init {
        _forecastData.addSource(LocationRepository.data) {
            if (it.status == Status.SUCCESS) {
                it.data.getExactMatch().apply {
                    if (this == null) {
                        _forecastData.value = Result.unset()
                    } else {
                        WeatherRepository.getWeather(this.locationId)
                    }
                }
            } else {
                _forecastData.value = it.transformData()
            }
        }
        _forecastData.addSource(PostalCodeRepository.data) {
            if (it.status == Status.SUCCESS) {
                it.data.getExactMatch().apply {
                    if (this == null) {
                        _forecastData.value = Result.unset()
                    } else {
                        LocationRepository.getLocations(latitude, longitude)
                    }
                }
            } else {
                _forecastData.value = it.transformData()
            }
        }
        _forecastData.addSource(WeatherRepository.data) {
            _forecastData.value = it.transformData(it.data)
        }
    }

    fun submitLocation(location: String) {
        if (location.isDigitsOnly()) {
            PostalCodeRepository.getLocations(location)
        } else {
            LocationRepository.getLocations(location)
        }
    }

    fun refreshForecast() {
        val currentLocationId = forecastData.value?.data?.locationId
        if (currentLocationId != null) {
            WeatherRepository.getWeather(currentLocationId)
        }
    }
}