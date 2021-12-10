package pl.training.goodweather.commons.security

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val oauthService: OAuthService) : Interceptor {

    private val authorizationHeader = "Authorization"
    private val tokenType = "bearer"

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        oauthService.oauthResponse?.let {
            requestBuilder.addHeader(authorizationHeader, "$tokenType ${it.accessToken}")
        }
        return chain.proceed(requestBuilder.build())
    }

}