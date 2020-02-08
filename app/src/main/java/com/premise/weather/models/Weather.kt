package com.premise.weather.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

data class Weather(
    @SerializedName("consolidated_weather")
    val forecasts: List<Forecast>,
    @SerializedName("woeid")
    val locationId: Int,
    val title: String,
    val latt_long: String
)

data class Forecast(
    val id: Long,
    @SerializedName("weather_state_name")
    val weatherState: String,
    @SerializedName("wind_direction_compass")
    val windDirection: String,
    @SerializedName("applicable_date")
    val date: String,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("the_temp")
    val currentTemp: Double,
    @SerializedName("air_pressure")
    val pressure: Double,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("predictability")
    val chanceOfPrecipitation: Int
) {

    val dateValue: Date? get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
    val currentTempInFahrenheit: Int get() = ((9/5.0 * currentTemp) + 32).roundToInt()
}