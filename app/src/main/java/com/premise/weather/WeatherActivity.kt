package com.premise.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.premise.weather.extensions.hideSoftKeyboard
import com.premise.weather.livedata.Status
import kotlinx.android.synthetic.main.activity_main.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: ForecastListAdapter
    private lateinit var popupMenu: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[WeatherViewModel::class.java]
        setupViews()
        setupListeners()
        setupLiveData()
    }

    private fun setupViews() {
        adapter = ForecastListAdapter()
        forecast_list.adapter = adapter
        popupMenu = PopupMenu(this, location_input_layout).apply {
            setOnMenuItemClickListener { item ->
                location_input_edit_text.setText(item.title.toString().substringBefore(" ("))
                viewModel.submitLocation(item.itemId)
                true
            }
        }
    }

    private fun setupListeners() {
        location_submit_button.setOnClickListener {
            viewModel.submitLocation(location_input_edit_text.text.toString())
            hideSoftKeyboard(it)
        }
        swipe_refresh.setOnRefreshListener { viewModel.refreshForecast() }
    }

    @SuppressLint("SetTextI18n")
    private fun setupLiveData() {
        viewModel.locationData.observe(this, Observer {
            if (it == null || it.isEmpty()) {
                popupMenu.dismiss()
            } else {
                popupMenu.menu.clear()
                it.forEach { location ->
                    popupMenu.menu.add(
                        Menu.NONE,
                        location.locationId,
                        Menu.NONE,
                        "${location.title} (${location.latt_long})"
                    )
                }
                popupMenu.show()
            }
        })

        viewModel.forecastData.observe(this, Observer {
            forecast_city_state.text = if (it.data == null) "" else "${it.data.title} (${it.data.latt_long})"
            adapter.submitList(it.data?.forecasts)
            swipe_refresh.isRefreshing = it.status == Status.LOADING
            swipe_refresh.isEnabled = it.status == Status.SUCCESS || it.status == Status.LOADING
        })
    }
}
