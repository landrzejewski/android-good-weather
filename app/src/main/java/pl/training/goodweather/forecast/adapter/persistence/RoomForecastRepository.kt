package pl.training.goodweather.forecast.adapter.persistence

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Completable.concat
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.model.DayForecast
import java.util.*

class RoomForecastRepository(private val forecastDao: ForecastDao) : ForecastRepository {

    companion object {

        const val CITY_ID = 1L

    }

    override fun save(city: String, forecast: List<DayForecast>): Completable {
        val operations = listOf(forecastDao.save(CityEntity(CITY_ID, city)), forecastDao.save(forecast.map(::toEntity)))
        return concat(operations).subscribeOn(Schedulers.io())
    }

    override fun getAll(city: String): Maybe<List<DayForecast>> = forecastDao.findAll(city)
        .map { it.forecast.map(::toModel) }
        .subscribeOn(Schedulers.io())

    override fun deleteAll(): Completable = forecastDao.deleteAll()
        .subscribeOn(Schedulers.io())

    private fun toEntity(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastEntity(null, icon, description, temperature, pressure, date.time, CITY_ID)
    }

    private fun toModel(dayForecastEntity: DayForecastEntity) = with(dayForecastEntity) {
        DayForecast(icon, description, temperature, pressure, Date(date))
    }

}