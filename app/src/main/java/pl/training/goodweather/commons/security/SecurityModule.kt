package pl.training.goodweather.commons.security

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.training.goodweather.commons.logging.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class SecurityModule {

    @Singleton
    @Provides
    fun oauthApi(): OAuthApi = Retrofit.Builder()
        .baseUrl("https://dangerous-dodo-75.loca.lt/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OAuthApi::class.java)

    @Singleton
    @Provides
    fun oauthService(oauthApi: OAuthApi, logger: Logger) = OAuthService(oauthApi, "goodweather", "goodweather-mobile", logger)

    @Singleton
    @Provides
    fun tokenInterceptor(oauthService: OAuthService) = TokenInterceptor(oauthService)

}