package pl.training.goodweather.commons.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment

fun Fragment.setProperty(key: String, value: String, context: Context = requireContext()) = context.getSharedPreferences(context.packageName, MODE_PRIVATE)
    .edit()
    .putString(key, value)
    .apply()

fun Fragment.getProperty(key: String, default: String = "", context: Context = requireContext()) = context.getSharedPreferences(context.packageName, MODE_PRIVATE)
    .getString(key, default)