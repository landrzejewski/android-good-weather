package pl.training.goodweather.forecast.api

import pl.training.goodweather.forecast.model.DayForecast

interface ForecastProvider {

    suspend fun getForecast(city: String): List<DayForecast>

}