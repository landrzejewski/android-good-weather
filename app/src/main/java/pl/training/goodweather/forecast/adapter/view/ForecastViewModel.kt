package pl.training.goodweather.forecast.adapter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.commons.forecastService

internal class ForecastViewModel : ViewModel() {

    private val forecastService = forecastService()
    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            forecastData.value = forecastService.getForecast(city).map(::toViewModel)
        }
    }

}