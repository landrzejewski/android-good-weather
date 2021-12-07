package pl.training.goodweather.forecast.adapter.view

import java.io.Serializable

data class DayForecastViewModel(
    val icon: String,
    val description: String,
    var temperature: String,
    val pressure: String,
    val date: String
) : Serializable