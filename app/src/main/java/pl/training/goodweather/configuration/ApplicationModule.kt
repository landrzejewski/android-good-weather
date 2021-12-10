package pl.training.goodweather.configuration

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.training.goodweather.commons.UserSettings
import pl.training.goodweather.commons.logging.AndroidLogger
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.commons.security.TokenInterceptor
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Singleton
    @Provides
    fun context(): Context = application

    @Singleton
    @Provides
    fun logger(): Logger = AndroidLogger()

    @Singleton
    @Provides
    fun httpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return OkHttpClient().newBuilder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun database(context: Context): ApplicationDatabase = Room.databaseBuilder(context, ApplicationDatabase::class.java, "database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun userSettings(context: Context): UserSettings {
        val userSettings = UserSettings(context)
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(userSettings)
        return userSettings
    }

}