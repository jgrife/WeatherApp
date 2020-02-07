package com.premise.weather.extensions

import com.premise.weather.models.Location

/**
 * an exact match is returned when we have one [Location], or when the [Location] has distance defined. When it has distance defined,
 * that means that there was a lat/long search and so we return the first results as the feed returns back a list of cities ordered by
 * distance from the lat/long passed in
 */
fun List<Location>?.getExactMatch(): Location? = if (this?.size == 1 || this?.first()?.distance != null) this.first() else null

fun List<Location>?.isExactMatch(): Boolean = getExactMatch() != null