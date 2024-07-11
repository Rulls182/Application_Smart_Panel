package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpanel.RetrofitClient.retrofit
import com.example.smartpanel.model.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btnLogin)

        val clickableText = findViewById<TextView>(R.id.Regist)
        clickableText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val usernameUser = email.text.toString().trim()
            val passwordUser = password.text.toString().trim()

            if (usernameUser.isEmpty() || passwordUser.isEmpty()) {
                showToast("Email and password must not be empty")
                return@setOnClickListener
            }

            val user = LoginModel(usernameUser, passwordUser)
            val call: Call<LoginResponseData> = retrofit.create(ApiService::class.java).loginUser(user)

            call.enqueue(object : Callback<LoginResponseData> {
                override fun onResponse(call: Call<LoginResponseData>, response: Response<LoginResponseData>) {
                    if (response.isSuccessful) {
                        val responseData: LoginResponseData? = response.body()
                        showToast("Login successful")
                        val intent = Intent(this@LoginActivity, RoomActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showToast("Login failed: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponseData>, t: Throwable) {
                    showToast("Login failed: ${t.message}")
                    Log.e("LoginError", "Error message: ${t.message}")
                }
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }
}