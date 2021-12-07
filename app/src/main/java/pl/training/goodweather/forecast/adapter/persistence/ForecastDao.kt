package pl.training.goodweather.forecast.adapter.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ForecastDao {

    @Insert(onConflict = IGNORE)
    suspend fun save(cityEntity: CityEntity)

    @Insert
    suspend fun save(forecast: List<DayForecastEntity>)

    @Transaction
    @Query("select * from CityEntity where name = :city")
    suspend fun findAll(city: String): ForecastEntity?

    @Query("delete from DayForecastEntity")
    suspend fun deleteAll()

}