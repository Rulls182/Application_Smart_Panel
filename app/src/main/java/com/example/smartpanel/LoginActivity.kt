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
import com.google.firebase.auth.FirebaseAuth
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

                if (usernameUser.isNotEmpty() && passwordUser.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(username.toString(),
                        password.toString()
                    )
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Login berhasil
                                Toast.makeText(this.applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this.applicationContext, LampActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Login gagal
                                Toast.makeText(this.applicationContext, "Gagal login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    // Kolom email atau password kosong
                    Toast.makeText(this.applicationContext, "Email atau Password Kosong", Toast.LENGTH_SHORT).show()
                }
            }






        }


//        fun navigateToDashboard(view: android.view.View) {
//            val intent = Intent(this, LampActivity::class.java)
//            startActivity(intent)
//        }
    }
}