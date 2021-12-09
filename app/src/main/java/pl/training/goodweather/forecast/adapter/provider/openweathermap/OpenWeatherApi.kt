package pl.training.goodweather.forecast.adapter.provider.openweathermap

import io.reactivex.rxjava3.core.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("forecast/daily?units=metric&appid=b933866e6489f58987b2898c89f542b8")
    fun getForecast(@Query("q") city: String, @Query("cnt") numberOfDays: Int) : Maybe<ResponseTransferObject>

}