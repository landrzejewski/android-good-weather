package pl.training.goodweather.tracking.adapter.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.commons.formatPace
import pl.training.goodweather.commons.formatSpeed
import pl.training.goodweather.commons.formatTime
import pl.training.goodweather.commons.security.OAuthService
import pl.training.goodweather.tracking.model.ActivityService
import pl.training.goodweather.tracking.model.Position
import java.lang.Exception
import javax.inject.Inject

class TrackingViewModel : ViewModel() {

    @Inject
    lateinit var activityService: ActivityService
    @Inject
    lateinit var oauthService: OAuthService

    var speed = ""
    var pace = ""
    var duration = ""

    init {
        componentsGraph.inject(this)
        viewModelScope.launch {
            try {
                oauthService.authenticate("jan", "123")
            } catch (exception: Exception) {
                Log.d("####","Login failed")
            }
        }
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