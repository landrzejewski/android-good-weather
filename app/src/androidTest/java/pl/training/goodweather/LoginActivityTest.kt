package pl.training.goodweather

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.training.goodweather.security.adapter.view.LoginActivity

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    var activityRule: ActivityScenario<LoginActivity> = ActivityScenario.launch(LoginActivity::class.java)

    @Test
    fun testLogin() {
        onView(withId(R.id.loginEditText))
            .perform(replaceText("Jan"), closeSoftKeyboard())
        Thread.sleep(10_000)
    }

}