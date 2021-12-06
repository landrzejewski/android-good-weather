package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import pl.training.goodweather.R

internal class ForecastActivity : AppCompatActivity() {

    private val viewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        viewModel.forecast.observe(this, ::onForecastRefreshed)
        viewModel.refreshForecast("warsaw")
    }

    private fun onForecastRefreshed(forecast: List<DayForecastViewModel>) {
        Log.d("###",forecast.toString())
    }

}