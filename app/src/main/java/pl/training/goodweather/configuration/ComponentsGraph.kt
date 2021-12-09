package pl.training.goodweather.configuration

import dagger.Component
import pl.training.goodweather.forecast.ForecastModule
import pl.training.goodweather.forecast.adapter.view.ForecastFragment
import pl.training.goodweather.forecast.adapter.view.ForecastViewModel
import pl.training.goodweather.tracking.TrackingModule
import pl.training.goodweather.tracking.adapter.view.TrackingViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ForecastModule::class, TrackingModule::class])
interface ComponentsGraph {

    fun inject(forecastViewModel: ForecastViewModel)

    fun inject(trackingViewModel: TrackingViewModel)

    fun inject(forecastFragment: ForecastFragment)

}