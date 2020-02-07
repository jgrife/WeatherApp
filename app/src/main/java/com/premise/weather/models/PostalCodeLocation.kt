package com.premise.weather.models

import com.google.gson.annotations.SerializedName

fun PostalCodeLocation?.getExactMatch(): Place? = if (this != null && places.size == 1) places.first() else null

data class PostalCodeLocation(
    @SerializedName("post code")
    val postalCode: String,
    val places: List<Place>
)

data class Place(
    @SerializedName("place name")
    val city: String,
    @SerializedName("state abbreviation")
    val state: String,
    val latitude: String,
    val longitude: String
)