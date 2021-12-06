package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import pl.training.goodweather.commons.hideKeyboard
import pl.training.goodweather.commons.setDrawable
import pl.training.goodweather.databinding.ActivityForecastBinding

internal class ForecastActivity : AppCompatActivity() {

    private val viewModel: ForecastViewModel by viewModels()
    private val forecastListAdapter = ForecastListAdapter()
    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        bindViews()
    }

    private fun initViews() {
        binding.forecastRecyclerView.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        binding.forecastRecyclerView.adapter = forecastListAdapter
    }

    private fun bindViews() {
        viewModel.forecast.observe(this, ::updateView)
        binding.checkButton.setOnClickListener {
            it.hideKeyboard()
            val cityName = binding.cityNameEditText.text.toString()
            viewModel.refreshForecast(cityName)
        }
    }

    private fun updateView(forecast: List<DayForecastViewModel>) {
        with(forecast.first()) {
            binding.iconImage.setDrawable(icon)
            binding.descriptionTextView.text = description
            binding.temperatureTextView.text = temperature
            binding.pressureTextView.text = pressure
        }
        forecastListAdapter.update(forecast.drop(1))
    }

}