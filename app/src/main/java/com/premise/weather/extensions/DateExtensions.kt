package com.premise.weather.extensions

import android.content.Context
import android.text.format.DateUtils
import com.premise.weather.R
import java.text.SimpleDateFormat
import java.util.*

fun Date?.isToday(): Boolean = if (this != null) DateUtils.isToday(time) else false

fun Date?.isTomorrow(): Boolean = if (this != null) DateUtils.isToday(time - DateUtils.DAY_IN_MILLIS) else false

fun Date?.getDateForDisplay(context: Context): String {
    return when {
        this == null -> ""
        isToday() -> context.getString(R.string.today)
        isTomorrow() -> context.getString(R.string.tomorrow)
        else -> SimpleDateFormat("EEE, MMM d, YYYY", Locale.getDefault()).format(this)
    }
}