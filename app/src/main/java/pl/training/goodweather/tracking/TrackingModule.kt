package pl.training.goodweather.tracking

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import dagger.Module
import dagger.Provides
import pl.training.goodweather.tracking.model.ActivityService
import javax.inject.Singleton

@Module
class TrackingModule {

    @Singleton
    @Provides
    fun activityService() = ActivityService()

    @Singleton
    @Provides
    fun locationRequest(): LocationRequest {
        val locationRequest = LocationRequest()
        locationRequest.priority = PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 1F
        return locationRequest
    }

}