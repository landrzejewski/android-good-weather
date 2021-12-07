package pl.training.goodweather.commons

import pl.training.goodweather.commons.logging.AndroidLogger
import pl.training.goodweather.forecast.adapter.provider.FakeForecastProvider
import pl.training.goodweather.forecast.adapter.provider.openweathermap.OpenWeatherApi
import pl.training.goodweather.forecast.adapter.provider.openweathermap.OpenWeatherProvider
import pl.training.goodweather.forecast.model.ForecastService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val fakeForecastService = ForecastService(FakeForecastProvider())

private val openWeatherApi = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(OpenWeatherApi::class.java)

private val openWeatherForecastService = ForecastService(OpenWeatherProvider(openWeatherApi))

fun forecastService() = openWeatherForecastService

fun logger() = AndroidLogger()