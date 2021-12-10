package pl.training.goodweather.security.adapter.view

import android.app.ActivityManager
import android.content.Intent
import android.content.Intent.ACTION_SCREEN_OFF
import android.content.Intent.ACTION_SCREEN_ON
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pl.training.goodweather.MainActivity
import pl.training.goodweather.commons.ExampleService
import pl.training.goodweather.commons.ScreenOffReceiver
import pl.training.goodweather.commons.ScreenOffService
import pl.training.goodweather.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()
        //firebaseExamples()
        componentsExamples()
    }

    private fun bindViews() {
        binding.loginButton.setOnClickListener {
            val login = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(login, password, ::onAuthentication)
        }
    }

    private fun onAuthentication(result: Boolean) {
        if (result) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //Toast.makeText(this, "Invalid login or password", Toast.LENGTH_LONG).show()
            Snackbar.make(binding.root, "Invalid login or password", Snackbar.LENGTH_LONG).show()
        }
    }

    // -------------------------------------- firebase ---------------------------------------------

    private val db = Firebase.firestore

    private fun firebaseExamples() {
        val user = mapOf(
            "firstName" to "Jan",
            "lastName" to "Kowalski",
            "email" to "jan.kowalski@training.pl"
        )

        val usersCollection = db.collection("users")

        /*usersCollection.add(user)
            .addOnSuccessListener { Log.d("###", "Document ${it.id} saved") }
            .addOnFailureListener { Log.d("###", "Document save failed") }*/

        usersCollection.whereEqualTo("firstName", "Jan")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    Log.d("###", "Document ${document.data}")
                }
            }

        usersCollection.document("1uhe14BLhLUdhnN28mIU")
            .addSnapshotListener { document, error ->
                if (document != null) {
                    Log.d("###", "Update ${document.data}")
                }
            }

        usersCollection.document("1uhe14BLhLUdhnN28mIU").delete()
    }

    private lateinit var exampleService: ExampleService

    private fun componentsExamples() {
        /*val intent = Intent(this, ScreenOffService::class.java)
        startService(intent)*/

        exampleService = ExampleService()
        val intent = Intent(this, exampleService::class.java)
        if (!isServiceRunning(ExampleService::class.java)) {
            startService(intent)
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("###", "Service is running")
                return true
            }
        }
        Log.i("###", "Service is not running")
        return false
    }

}