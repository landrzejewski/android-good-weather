package pl.training.goodweather.forecast.adapter.provider.openweathermap

import com.google.gson.annotations.SerializedName

internal data class DayForecastTransferObject(
    @SerializedName("temp") val temperature: TemperatureTransferObject,
    val pressure: Double,
    @SerializedName("dt") val date: Long,
    val weather: List<WeatherTransferObject>
)