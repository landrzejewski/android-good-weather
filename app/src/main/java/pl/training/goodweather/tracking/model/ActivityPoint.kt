package pl.training.goodweather.tracking.model

data class ActivityPoint(
    var id: Long?,
    val activityId: String,
    val timestamp: Long,
    val distance: Float,
    val speed: Float,
    val pace: Double,
    val duration: Long,
    val position: Position
)