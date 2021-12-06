package pl.training.goodweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "### onCreate")
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener(::sayHello)
    }

    private fun sayHello(button: View) {
        findViewById<TextView>(R.id.text).text = getString(R.string.hello_message_response)
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "### onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "### onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "### onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "### onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "### onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "### onDestroy")
    }

}