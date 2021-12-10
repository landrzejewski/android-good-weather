package pl.training.goodweather.commons.security

import com.google.gson.annotations.SerializedName

data class OAuthResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: String,
    @SerializedName("refresh_expires_in") val refreshTokenExpiresIn: String,
)