package pl.training.goodweather.forecast.model

import pl.training.goodweather.forecast.api.ForecastProvider

class ForecastService(private val forecastProvider: ForecastProvider) {

    suspend fun getForecast(city: String) = forecastProvider.getForecast(city)

}