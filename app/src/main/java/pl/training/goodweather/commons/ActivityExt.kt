package pl.training.goodweather.commons

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE

fun AppCompatActivity.setProperty(key: String, value: String) = getSharedPreferences(packageName, MODE_PRIVATE)
    .edit()
    .putString(key, value)
    .apply()

fun AppCompatActivity.getProperty(key: String, default: String = "") = getSharedPreferences(packageName, MODE_PRIVATE)
    .getString(key, default)