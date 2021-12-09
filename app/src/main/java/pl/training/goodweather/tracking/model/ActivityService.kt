package pl.training.goodweather.tracking.model

import java.util.*

class ActivityService {

    private var startTime = 0L
    private var activityId = ""
    private var totalDistance = 0F

    fun startActivity() {
        reset()
    }

    private fun reset() {
        startTime = System.currentTimeMillis()
        activityId = UUID.randomUUID().toString()
        totalDistance = 0F
    }

    fun createActivityPoint(position: Position, speed: Float, distance: Float) {
        val currentTime = System.currentTimeMillis()
        val duration = startTime - currentTime
        totalDistance += distance
        var pace = 0.0
        if (totalDistance > 0) {
            pace = duration.toDouble() / (1_000 * 60) / totalDistance / 1_000
        }
        var activityPoint = ActivityPoint(null, activityId, currentTime, distance, speed, pace, duration, position)
        // save to database
    }

}