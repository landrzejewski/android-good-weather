package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import pl.training.goodweather.R
import pl.training.goodweather.commons.getProperty
import pl.training.goodweather.commons.hideKeyboard
import pl.training.goodweather.commons.setDrawable
import pl.training.goodweather.commons.setProperty
import pl.training.goodweather.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    companion object {

        const val CITY_KEY = "cityName"
        const val DEFAULT_CITY_NAME = "warsaw"

    }

    private val viewModel: ForecastViewModel by activityViewModels()
    private val forecastListAdapter = ForecastListAdapter()
    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        loadLatestForecast()
    }

    private fun initViews() {
        binding.forecastRecyclerView.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.forecastRecyclerView.adapter = forecastListAdapter
    }

    private fun bindViews() {
        viewModel.forecast.observe(viewLifecycleOwner, ::updateView)
        binding.checkButton.setOnClickListener {
            it.hideKeyboard()
            val cityName = binding.cityNameEditText.text.toString()
            setProperty(CITY_KEY, cityName)
            viewModel.refreshForecast(cityName)
        }
        binding.iconImage.setOnClickListener {
            findNavController().navigate(R.id.show_day_forecast_details)
        }
        forecastListAdapter.tapListener = {
            viewModel.currentDayForecast = it
            findNavController().navigate(R.id.show_day_forecast_details)
        }
    }

    private fun loadLatestForecast() {
        getProperty(CITY_KEY, DEFAULT_CITY_NAME)?.let {
            viewModel.refreshForecast(it)
            binding.cityNameEditText.setText(it)
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