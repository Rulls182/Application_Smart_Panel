package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var Regist: TextView
    private lateinit var btnLogin: Button

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        Regist = findViewById(R.id.Regist)
        btnLogin = findViewById(R.id.btnLogin)

        val clickableText = findViewById<TextView>(R.id.Regist)
        clickableText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            btnLogin.setOnClickListener {
                val usernameUser = username.text.toString()
                val passwordUser = password.text.toString()

                val database = FirebaseDatabase.getInstance().getReference("users")

                if (usernameUser.isEmpty() || passwordUser.isEmpty()) {
                    Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
                } else {
                    database.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.child(usernameUser).exists()) {
                                val savedPassword = snapshot.child(usernameUser).child("password")
                                    .getValue(String::class.java)
                                if (savedPassword == passwordUser) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Login Berhasil",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val masuk = Intent(this@LoginActivity, LampActivity::class.java)
                                    startActivity(masuk)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Password Salah",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Data Belum Terdaftar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
            }


        }


//        fun navigateToDashboard(view: android.view.View) {
//            val intent = Intent(this, LampActivity::class.java)
//            startActivity(intent)
//        }
    }
}