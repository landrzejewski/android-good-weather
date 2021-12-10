package pl.training.goodweather.commons.security

import pl.training.goodweather.commons.logging.Logger

class OAuthService(private val oauthApi: OAuthApi, private val realmName: String, private val clientId: String, private val logger: Logger) {

    var oauthResponse: OAuthResponse? = null

    suspend fun authenticate(login: String, password: String) {
        oauthResponse = oauthApi.authenticate(realmName, clientId, login, password)
        logger.log("Authentication: $oauthResponse")
    }

    suspend fun refreshToken() {
        oauthResponse?.let {
            oauthResponse = oauthApi.refreshToken(realmName, clientId, it.refreshToken)
            logger.log("Authentication: $oauthResponse")
        }
    }

}