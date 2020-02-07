package com.premise.weather

import android.app.Application
import android.content.Context
import android.content.res.Resources

class WeatherApp : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: WeatherApp

        val appContext: Context get() = instance.applicationContext
        val resources: Resources get() = instance.resources
    }
}