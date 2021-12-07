package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.training.goodweather.R
import pl.training.goodweather.databinding.ActivityDayForecastBinding
import pl.training.goodweather.forecast.adapter.view.ForecastActivity.Companion.DAY_FORECAST_KEY

class DayForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDayForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_forecast)
        binding = ActivityDayForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dayForecast = intent.getSerializableExtra(DAY_FORECAST_KEY) as DayForecastViewModel
        binding.descriptionTextView.text = dayForecast.description
    }

}