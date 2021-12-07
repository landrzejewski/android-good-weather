package pl.training.goodweather.forecast.adapter.provider.openweathermap

import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenWeatherApi {

    @GET("forecast/daily?units=metric&appid=b933866e6489f58987b2898c89f542b8")
    suspend fun getForecast(@Query("q") city: String) : ResponseTransferObject

}