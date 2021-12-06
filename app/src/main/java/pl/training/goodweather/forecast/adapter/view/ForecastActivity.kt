package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import pl.training.goodweather.databinding.ActivityForecastBinding

internal class ForecastActivity : AppCompatActivity() {

    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()
    }

    private fun bindViews() {
        viewModel.forecast.observe(this, ::updateView)
        binding.checkButton.setOnClickListener {
            val cityName = binding.cityNameEditText.text.toString()
            viewModel.refreshForecast(cityName)
        }
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        with(forecast.first()) {
           // binding.iconImage.setImageDrawable(getDrawable(R.id.))
            binding.descriptionTextView.text = description
            binding.temperatureTextView.text = temperature
            binding.pressureTextView.text = pressure
        }
    }

}