package pl.training.goodweather.forecast.adapter.persistence

import android.util.Log
import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.model.DayForecast

class RoomForecastRepository(private val forecastDao: ForecastDao) : ForecastRepository {

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        val cityEntity = CityEntity(null, city)
        forecastDao.save(cityEntity)
        Log.d("###", "")
    }

    override suspend fun getAll(city: String): List<DayForecast> {
       return emptyList()
    }

    override suspend fun deleteAll() {

    }

}