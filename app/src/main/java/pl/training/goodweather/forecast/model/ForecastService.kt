package pl.training.goodweather.forecast.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.concat
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import pl.training.goodweather.commons.UserSettings
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.configuration.Values
import pl.training.goodweather.configuration.Values.FORECAST_CACHE_ENABLED
import pl.training.goodweather.configuration.Values.FORECAST_NUMBER_OF_DAYS
import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.api.ForecastRepository

class ForecastService(private val forecastProvider: ForecastProvider, private val forecastRepository: ForecastRepository,
                      userSettings: UserSettings, private val logger: Logger) {

    private var numberOfDays: Int
    private var cacheEnabled: Boolean
    private val disposables = CompositeDisposable()

    init {
        numberOfDays = userSettings.get(FORECAST_NUMBER_OF_DAYS, "7").toInt()
        cacheEnabled = userSettings.get(FORECAST_CACHE_ENABLED, true)
        userSettings.settingsChanges
            .subscribe {
                when(it.first) {
                    FORECAST_NUMBER_OF_DAYS -> numberOfDays = it.second.toInt()
                    FORECAST_CACHE_ENABLED -> cacheEnabled = it.second.toBoolean()
                }
            }
            .addTo(disposables)
    }

    fun getForecast(city: String): Observable<List<DayForecast>> {
        val cachedForecast = forecastRepository.getAll(city).toObservable()
        val forecast = forecastRepository.deleteAll()
            .andThen(forecastProvider.getForecast(city, numberOfDays))
            .doOnSuccess { onForecastLoaded(city, it) }
            .toObservable()
        return if (cacheEnabled) concat(cachedForecast, forecast) else forecast
    }

    private fun onForecastLoaded(city: String, forecast: List<DayForecast>) {
        forecastRepository.save(city, forecast)
            .subscribe({ logger.log("Forecast saved") }, { logger.log("Save forecast failed") })
    }

}