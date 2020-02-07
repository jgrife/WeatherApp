package com.premise.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.premise.weather.WeatherApp.Companion.appContext
import com.premise.weather.WeatherApp.Companion.resources
import com.premise.weather.extensions.getDateForDisplay
import com.premise.weather.models.Forecast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_forecast.*

class ForecastListAdapter : ListAdapter<Forecast, ForecastListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).apply {
            holder.item_forecast_date.text = dateValue.getDateForDisplay(appContext)
            holder.item_forecast_temperature_value.text = resources.getString(R.string.fahrenheit_temperature, currentTempInFahrenheit)
            holder.item_forecast_pressure_value.text = pressure.toString()
            holder.item_forecast_humidity_value.text = humidity.toString()
            holder.item_forecast_current_condition_value.text = weatherState
            holder.item_forecast_chance_of_precipitation_value.text = resources.getString(R.string.precipitation_percentage, chanceOfPrecipitation)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Forecast> = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldForecast: Forecast, newForecast: Forecast): Boolean {
                return oldForecast.id == newForecast.id
            }

            override fun areContentsTheSame(oldForecast: Forecast, newForecast: Forecast) = oldForecast.date != newForecast.date
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}