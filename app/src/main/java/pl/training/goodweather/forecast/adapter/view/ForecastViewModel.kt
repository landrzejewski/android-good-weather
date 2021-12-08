package pl.training.goodweather.forecast.adapter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.launch
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.forecast.model.DayForecast
import pl.training.goodweather.forecast.model.ForecastService
import java.util.*
import javax.inject.Inject

class ForecastViewModel : ViewModel() {

    @Inject
    lateinit var forecastService: ForecastService
    @Inject
    lateinit var logger: Logger
    private val disposables = CompositeDisposable()

    init {
        componentsGraph.inject(this)
    }

    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData
    var selectedDayForecastDate: String? = null

    fun refreshForecast(city: String) {
       forecastService.getForecast(city)
           .subscribe({onForecastLoaded(it.map(::toViewModel))}, {logger.log("Exception: $it")})
           .addTo(disposables)
    }

    private fun onForecastLoaded(forecast: List<DayForecastViewModel>) {
        if (forecast.isNotEmpty()) {
            forecastData.postValue(forecast)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}