package pl.training.goodweather.forecast.adapter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.commons.formatDate
import pl.training.goodweather.commons.formatPressure
import pl.training.goodweather.commons.formatTemperature
import pl.training.goodweather.forecast.adapter.provider.FakeForecastProvider
import pl.training.goodweather.forecast.model.DayForecast
import pl.training.goodweather.forecast.model.ForecastService

internal class ForecastViewModel : ViewModel() {

    private val forecastService = ForecastService(FakeForecastProvider())
    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            forecastData.value = forecastService.getForecast(city).map(::toViewModel)
        }
    }

    private fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(icon, description, formatTemperature(temperature), formatPressure(pressure), formatDate(date))
    }

}