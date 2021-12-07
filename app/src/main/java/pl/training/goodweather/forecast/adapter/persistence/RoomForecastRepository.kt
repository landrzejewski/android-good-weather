package pl.training.goodweather.forecast.adapter.persistence

import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.model.DayForecast
import java.util.*

class RoomForecastRepository(private val forecastDao: ForecastDao) : ForecastRepository {

    companion object {

        const val CITY_ID = 1L

    }

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        forecastDao.save(CityEntity(CITY_ID, city))
        forecastDao.save(forecast.map(::toEntity))
    }

    override suspend fun getAll(city: String) = forecastDao.findAll(city)?.forecast?.map(::toModel) ?: emptyList()

    override suspend fun deleteAll() {
        forecastDao.deleteAll()
    }

    private fun toEntity(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastEntity(null, icon, description, temperature, pressure, date.time, CITY_ID)
    }

    private fun toModel(dayForecastEntity: DayForecastEntity) = with(dayForecastEntity) {
        DayForecast(icon, description, temperature, pressure, Date(date))
    }

}