package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.training.goodweather.R
import pl.training.goodweather.forecast.adapter.provider.FakeForecastProvider
import pl.training.goodweather.forecast.model.ForecastService

internal class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.text).text = ""//forecastService.getForecast("warsaw")[0].description
    }

}