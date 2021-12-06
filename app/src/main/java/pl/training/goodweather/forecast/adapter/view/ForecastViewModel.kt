package pl.training.goodweather.forecast.adapter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.forecast.adapter.provider.FakeForecastProvider
import pl.training.goodweather.forecast.model.DayForecast
import pl.training.goodweather.forecast.model.ForecastService

internal class ForecastViewModel : ViewModel() {

    private val forecastService = ForecastService(FakeForecastProvider())
    private val forecastData = MutableLiveData<List<DayForecast>>()

    val forecast: LiveData<List<DayForecast>> = forecastData

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            forecastData.value = forecastService.getForecast(city);
        }
    }

}