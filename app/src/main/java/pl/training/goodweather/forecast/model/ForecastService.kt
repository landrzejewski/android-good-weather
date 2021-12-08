package pl.training.goodweather.forecast.model

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.concat
import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.api.ForecastRepository

class ForecastService(private val forecastProvider: ForecastProvider, private val forecastRepository: ForecastRepository) {

    fun getForecast(city: String): Observable<List<DayForecast>> {
        val cachedForecast = forecastRepository.getAll(city).toObservable()
        val forecast = forecastProvider.getForecast(city)
            .flatMap {
                forecastRepository.deleteAll()
                    .andThen(forecastRepository.save(city, it))
                    .andThen(Maybe.just(it))
            }
            .toObservable()
        return concat(cachedForecast, forecast)
    }

}