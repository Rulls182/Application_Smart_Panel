package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btnRegist: Button

    // Referensi Firebase Authentication
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    // Referensi Firebase Database
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.email)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnRegist = findViewById(R.id.btnRegist)

        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        btnRegist.setOnClickListener {
            val emailUser = email.text.toString().trim()
            val userName = username.text.toString().trim()
            val passwordUser = password.text.toString().trim()

            if (emailUser.isEmpty() || userName.isEmpty() || passwordUser.isEmpty()) {
                Toast.makeText(this, "Data Masih Ada yang Kosong", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(emailUser, userName, passwordUser)
            }
        }
    }

    private fun registerUser(email: String, username: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = mAuth.currentUser?.uid
                    if (userId != null) {
                        val userMap = hashMapOf<String, Any>(
                            "email" to email,
                            "username" to username,
                            "password" to password
                        )
                        databaseReference.child(userId).setValue(userMap)
                            .addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                                    val registerIntent = Intent(this, LoginActivity::class.java)
                                    startActivity(registerIntent)
                                } else {
                                    Toast.makeText(this, "Gagal Menyimpan Data Pengguna", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "Registrasi Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
