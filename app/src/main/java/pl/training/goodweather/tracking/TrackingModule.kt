package pl.training.goodweather.tracking

import dagger.Module
import dagger.Provides
import pl.training.goodweather.tracking.model.ActivityService
import javax.inject.Singleton

@Module
class TrackingModule {

    @Singleton
    @Provides
    fun activityService() = ActivityService()

}