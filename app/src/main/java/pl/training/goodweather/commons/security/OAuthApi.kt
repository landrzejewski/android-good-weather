package pl.training.goodweather.commons.security

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface OAuthApi {

    @FormUrlEncoded
    @POST("auth/realms/{realm}/protocol/openid-connect/token")
    suspend fun authenticate(
        @Path("realm") realm: String,
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password"
    ): OAuthResponse

    @FormUrlEncoded
    @POST("auth/realms/{realm}/protocol/openid-connect/token")
    suspend fun refreshToken(
        @Path("realm") realm: String,
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token"
    ): OAuthResponse

}