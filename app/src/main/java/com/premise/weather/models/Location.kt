package com.premise.weather.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("woeid")
    val locationId: Int,
    val title: String,
    val location_type: String,
    val latt_long: String,
    val distance: Int?)