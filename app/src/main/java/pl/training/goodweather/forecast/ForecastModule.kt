package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.training.goodweather.configuration.ApplicationDatabase
import pl.training.goodweather.forecast.adapter.persistence.ForecastDao
import pl.training.goodweather.forecast.adapter.persistence.RoomForecastRepository
import pl.training.goodweather.forecast.adapter.provider.FakeForecastProvider
import pl.training.goodweather.forecast.adapter.provider.openweathermap.OpenWeatherApi
import pl.training.goodweather.forecast.adapter.provider.openweathermap.OpenWeatherProvider
import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.model.ForecastService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ForecastModule {

    @Named("fake")
    @Singleton
    @Provides
    fun fakeForecastProvider(): ForecastProvider = FakeForecastProvider()

    @Singleton
    @Provides
    fun openWeatherApi(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenWeatherApi::class.java)

    @Named("openweather")
    @Singleton
    @Provides
    fun openWeatherProvider(openWeatherApi: OpenWeatherApi): ForecastProvider = OpenWeatherProvider(openWeatherApi)

    @Singleton
    @Provides
    fun forecastDao(database: ApplicationDatabase) = database.forecastDao()

    @Singleton
    @Provides
    fun roomForecastRepository(forecastDao: ForecastDao): ForecastRepository = RoomForecastRepository(forecastDao)

    @Singleton
    @Provides
    fun forecastService(@Named("openweather") forecastProvider: ForecastProvider, forecastRepository: ForecastRepository) = ForecastService(forecastProvider, forecastRepository)

}