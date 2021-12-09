package pl.training.goodweather.commons

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.ClassCastException

class UserSettings(private val context: Context) : SharedPreferences.OnSharedPreferenceChangeListener {

    private val subject = PublishSubject.create<Pair<String, String>>()

    val settingsChanges: Observable<Pair<String, String>> = subject

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        val value = try {
           get(key, "", sharedPreferences)
        } catch (exception: ClassCastException) {
             get(key, false, sharedPreferences).toString()
        }
        subject.onNext(Pair(key, value))
    }

    fun get(key: String, default: String = "", preferences: SharedPreferences = getDefaultSharedPreferences(context)) = preferences.getString(key, default) ?: default

    fun get(key: String, default: Boolean = false, preferences: SharedPreferences = getDefaultSharedPreferences(context)) = preferences.getBoolean(key, default)

    fun set(key: String, value: String) = getDefaultSharedPreferences(context).edit()
        .putString(key, value)
        .apply()

}