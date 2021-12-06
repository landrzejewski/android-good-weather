package pl.training.goodweather.forecast.model

import java.util.*

data class DayForecast(
    val icon: String,
    val description: String,
    var temperature: Double,
    val pressure: Double,
    val date: Date
)