package pl.training.goodweather.forecast.adapter.view

import pl.training.goodweather.commons.formatDate
import pl.training.goodweather.commons.formatPressure
import pl.training.goodweather.commons.formatTemperature
import pl.training.goodweather.forecast.model.DayForecast

fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
    DayForecastViewModel(icon, description, formatTemperature(temperature), formatPressure(pressure), formatDate(date))
}