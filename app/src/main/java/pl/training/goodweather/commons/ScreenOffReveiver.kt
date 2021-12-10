package pl.training.goodweather.commons

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SCREEN_OFF
import android.content.Intent.ACTION_SCREEN_ON
import android.util.Log

class ScreenOffReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (ACTION_SCREEN_OFF == intent.action) {
            Log.d("###", "Screen is off")
        } else if ((ACTION_SCREEN_ON == intent.action)) {
            Log.d("###", "Screen is on")
        }
    }

}