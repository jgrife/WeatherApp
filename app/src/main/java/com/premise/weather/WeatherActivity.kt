package com.premise.weather

import android.os.Bundle
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
        adapter = ForecastListAdapter()
        forecast_list.adapter = adapter
        setupViews()
        setupListeners()
        setupLiveData()
    }

    private fun setupViews() {
        popupMenu = PopupMenu(this, location_input_layout).apply {
            setOnMenuItemClickListener { item ->
                location_input_edit_text.setText(item.title)
                viewModel.submitLocation(location_input_edit_text.text.toString())
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

    private fun setupLiveData() {
        viewModel.locationData.observe(this, Observer {
            if (it == null || it.isEmpty()) {
                popupMenu.dismiss()
            } else {
                it.forEach { location -> popupMenu.menu.add(location.title) }
                popupMenu.show()
            }
        })

        viewModel.forecastData.observe(this, Observer {
            forecast_city_state.text = it.data?.title
            adapter.submitList(it.data?.forecasts)
            swipe_refresh.isRefreshing = it.status == Status.LOADING
            swipe_refresh.isEnabled = it.status == Status.SUCCESS || it.status == Status.LOADING
        })
    }
}
