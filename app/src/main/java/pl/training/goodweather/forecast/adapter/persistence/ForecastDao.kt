package pl.training.goodweather.forecast.adapter.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface ForecastDao {

    @Insert(onConflict = IGNORE)
    fun save(cityEntity: CityEntity): Completable

    @Insert
    fun save(forecast: List<DayForecastEntity>): Completable

    @Transaction
    @Query("select * from CityEntity where name = :city")
    fun findAll(city: String): Maybe<ForecastEntity>

    @Query("delete from DayForecastEntity")
    fun deleteAll(): Completable

}