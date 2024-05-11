package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpanel.model.LoginModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import retrofit2.Call


class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var Regist: TextView
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        Regist = findViewById(R.id.Regist)
        buttonLogin = findViewById(R.id.btnLogin)

        val clickableText = findViewById<TextView>(R.id.Regist)
        clickableText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
//
//            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//                insets
//            }
        }

        buttonLogin.setOnClickListener {
            val usernameUser = email.text.toString()
            val passwordUser = password.text.toString()

            val user = LoginModel(usernameUser, passwordUser)
            val call:Call<LoginResponseData> = apiService.loginUser(user)

            fetch(
                call,
                success = { response, header ->
                    if (header != null) {
                        if (response?.idToken !== null) {
                            val intent = Intent(this, LampActivity::class.java)
                            startActivity(intent)
                            finish()
                            showToast("Login successful")
                            // Save the email in SharedPreferences after successful login
                        } else {
                            showToast("Login failed: No data in the response")
                        }
                    }
                },
                error = { _, message ->
                    showToast("Login failed: $message")
                }
            )
        }






        }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

//        fun navigateToDashboard(view: android.view.View) {
//            val intent = Intent(this, LampActivity::class.java)
//            startActivity(intent)
//        }

}