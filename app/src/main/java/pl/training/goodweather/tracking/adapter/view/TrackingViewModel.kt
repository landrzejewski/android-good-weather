package pl.training.goodweather.tracking.adapter.view

import android.location.Location
import androidx.lifecycle.ViewModel
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.tracking.model.ActivityService
import pl.training.goodweather.tracking.model.Position
import javax.inject.Inject

class TrackingViewModel : ViewModel() {

    @Inject
    lateinit var activityService: ActivityService
    private var lastLocation: Location? = null

    var speed = ""
    var pace = ""
    var duration = ""

    init {
        componentsGraph.inject(this)
    }

    fun start() {
        activityService.startActivity()
    }

    fun onLocationChange(location: Location): Pair<Location?, Location> {
        val position = Position(location.longitude, location.latitude)
        var distance = 0F
        lastLocation?.let { distance = location.distanceTo(it) }
        activityService.createActivityPoint(position, location.speed, distance)
        val result = Pair(lastLocation, location)
        lastLocation = location
        return result
    }

}