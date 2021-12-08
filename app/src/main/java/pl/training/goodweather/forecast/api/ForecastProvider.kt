package pl.training.goodweather.forecast.api

import io.reactivex.rxjava3.core.Maybe
import pl.training.goodweather.forecast.model.DayForecast

interface ForecastProvider {

    fun getForecast(city: String): Maybe<List<DayForecast>>

}