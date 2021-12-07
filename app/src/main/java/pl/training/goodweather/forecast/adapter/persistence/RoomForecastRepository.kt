package pl.training.goodweather.forecast.adapter.persistence

import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.model.DayForecast

class RoomForecastRepository(private val forecastDao: ForecastDao) : ForecastRepository {

    companion object {

        const val CITY_ID = 1L

    }

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        forecastDao.save(CityEntity(CITY_ID, city))
    }

    override suspend fun getAll(city: String): List<DayForecast> {
       return emptyList()
    }

    override suspend fun deleteAll() {

    }

}