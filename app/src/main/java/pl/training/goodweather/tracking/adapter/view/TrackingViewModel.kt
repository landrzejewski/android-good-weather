package pl.training.goodweather.tracking.adapter.view

import androidx.lifecycle.ViewModel
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.commons.formatPace
import pl.training.goodweather.commons.formatSpeed
import pl.training.goodweather.commons.formatTime
import pl.training.goodweather.tracking.model.ActivityService
import pl.training.goodweather.tracking.model.Position
import javax.inject.Inject

class TrackingViewModel : ViewModel() {

    @Inject
    lateinit var activityService: ActivityService

    var speed = ""
    var pace = ""
    var duration = ""

    init {
        componentsGraph.inject(this)
    }

    fun start() {
        activityService.startActivity()
    }

    fun onLocationChange(position: Position, speed: Float, distance: Float) {
        val activityPoint = activityService.createActivityPoint(position, speed, distance)
        this.speed = formatSpeed(activityPoint.speed)
        pace = formatPace(activityPoint.pace)
        duration = formatTime(activityPoint.duration)
    }

}