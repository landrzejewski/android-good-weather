package pl.training.goodweather.forecast.adapter.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey var id: Long?,
    @ColumnInfo val name: String
)