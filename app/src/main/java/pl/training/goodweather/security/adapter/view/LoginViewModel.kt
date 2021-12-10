package pl.training.goodweather.security.adapter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.commons.security.OAuthService
import java.lang.Exception
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    @Inject
    lateinit var oauthService: OAuthService

    init {
        componentsGraph.inject(this)
    }

    fun login(login: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                oauthService.authenticate(login, password)
                callback(true)
            } catch (exception: Exception) {
                callback(false)
            }
        }
    }

}