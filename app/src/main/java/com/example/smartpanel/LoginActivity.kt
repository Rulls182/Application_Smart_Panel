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
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call


class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: Button
    private lateinit var auth: FirebaseAuth


    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btnLogin)

        val clickableText = findViewById<TextView>(R.id.Regist)
        clickableText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


//        buttonLogin.setOnClickListener {
//            val emailUser = email.text.toString().trim()
//            val passwordUser = password.text.toString().trim()

//            // Validate input
//            if (email.length() == 0|| password.length() == 0) {
//                Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (!isValidEmail(email.toString())) {
//                Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Call the signIn method
//            signIn(email, password)
//        }
//    }
//
//    private fun isValidEmail(email: String): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }

//    private fun signIn(email: EditText, password: EditText) {
//        auth.signInWithEmailAndPassword(email.toString(), password.toString())
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val user = auth.currentUser
//                    Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, RoomActivity::class.java)
////                            startActivity(intent)
////                            finish()
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

        val apiService = retrofit.create(ApiService::class.java)

        buttonLogin.setOnClickListener {
            val usernameUser = email.text.toString()
            val passwordUser = password.text.toString()

            if (usernameUser.isEmpty() || passwordUser.isEmpty()) {
                showToast("Email and password must not be empty")
                return@setOnClickListener
            }

            val user = LoginModel(usernameUser, passwordUser)
            val call: Call<LoginResponseData> = apiService.loginUser(user)

            fetch(
                call,
                success = { response, header ->
                    Log.d("LoginResponse", "Response: $response, Header: $header")
                    if (header != null) {
                        if (response?.idToken != null) {
                            val intent = Intent(this, RoomActivity::class.java)
                            startActivity(intent)
                            finish()
                            showToast("Login successful")
                            // Save the email in SharedPreferences after successful login
                        } else {
                            showToast("Login failed: No idToken in the response")
                        }
                    } else {
                        showToast("Login failed: No header in the response")
                    }
                },
                error = { _, message ->
                    showToast("Login failed: $message")
                    Log.e("LoginError", "Error message: $message")
                }
            )
        }


    }
}










//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//    }


