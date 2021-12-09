package pl.training.goodweather.forecast.adapter.provider

import io.reactivex.rxjava3.core.Maybe
import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.model.DayForecast
import java.util.*

class FakeForecastProvider : ForecastProvider {

    override fun getForecast(city: String, numberOfDays: Int): Maybe<List<DayForecast>> = Maybe.just(
        listOf(
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
            DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date())
        )
    )

}