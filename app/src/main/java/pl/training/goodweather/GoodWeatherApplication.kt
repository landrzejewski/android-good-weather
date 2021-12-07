package pl.training.goodweather

import android.app.Application
import pl.training.goodweather.configuration.ComponentsGraph
import pl.training.goodweather.configuration.DaggerComponentsGraph

class GoodWeatherApplication : Application() {

    companion object {

        lateinit var componentsGraph: ComponentsGraph

    }

    override fun onCreate() {
        super.onCreate()
        componentsGraph = DaggerComponentsGraph.builder()
            .build()
    }

}