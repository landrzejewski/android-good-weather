package pl.training.goodweather.commons

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log

class ScreenOffService : Service() {

    private lateinit var receiver: ScreenOffReceiver

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        receiver = ScreenOffReceiver()
        registerReceiver(receiver, intentFilter)
        Log.d("###", "Registering receiver")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        Log.d("###", "Unregistering receiver")
    }

}