package pl.training.goodweather.forecast.model

import io.reactivex.rxjava3.core.Observable
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.api.ForecastRepository

class ForecastService(private val forecastProvider: ForecastProvider, private val forecastRepository: ForecastRepository, private val logger: Logger) {

    fun getForecast(city: String): Observable<List<DayForecast>> {
        return forecastRepository.getAll(city).toObservable()
            .concatWith(forecastRepository.deleteAll()
                .andThen(forecastProvider.getForecast(city))
                .doOnSuccess { onForecastLoaded(city, it) }
            )
    }

    private fun onForecastLoaded(city: String, forecast: List<DayForecast>) {
        forecastRepository.save(city, forecast)
            .subscribe({ logger.log("Forecast saved") }, { logger.log("Save forecast failed") })
    }

}