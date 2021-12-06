package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.training.goodweather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener(::sayHello)
    }

    private fun sayHello(button: View) {
        findViewById<TextView>(R.id.text).text = getString(R.string.hello_message_response)
    }

}