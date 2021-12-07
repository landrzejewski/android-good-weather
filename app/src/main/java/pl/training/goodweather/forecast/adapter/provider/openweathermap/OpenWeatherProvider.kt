package pl.training.goodweather.forecast.adapter.provider.openweathermap

import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.model.DayForecast
import java.util.*

class OpenWeatherProvider(private val openWeatherApi: OpenWeatherApi) : ForecastProvider {

    private val icons = mapOf("01d" to "ic_sun", "02d" to "ic_cloud_sun", "03d" to "ic_cloud", "04d" to "ic_cloud",
        "09d" to "ic_cloud_rain", "10d" to "ic_cloud_sun_rain", "11d" to "ic_bolt", "13d" to "ic_snowflake", "50d" to "ic_wind")

    override suspend fun getForecast(city: String): List<DayForecast> {
       return openWeatherApi.getForecast(city).forecast.map(::toModel)
    }

    private fun toModel(dayForecastTransferObject: DayForecastTransferObject) = with(dayForecastTransferObject) {
        val weatherData = weather.first()
        val icon = icons[weatherData.description] ?: "ic_sun"
        DayForecast(icon, weatherData.description, temperature.day, pressure, Date(date * 1_000))
    }

}