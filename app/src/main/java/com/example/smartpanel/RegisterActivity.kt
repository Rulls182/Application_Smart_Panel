package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btnRegist: Button

    // referensi Firebase Authentication
    private val mAuth = FirebaseAuth.getInstance()
    // referensi Firebase Database
    private val mDatabase = FirebaseDatabase.getInstance().reference

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = findViewById(R.id.email)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnRegist = findViewById(R.id.btnRegist)

        btnRegist.setOnClickListener {
            val email = email.text.toString()
            val username = username.text.toString()
            val password = password.text.toString()

            registerUser(email, username, password)
        }
    }

    // Membuat fungsi untuk mendaftarkan pengguna
    private fun registerUser(email: String, username: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User berhasil regist, simpan informasi user ke dalam Firebase Database
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val userId = currentUser?.uid
                    val userData = hashMapOf(
                        "email" to email,
                        "username" to username,
                        "password" to password
                    )
                    if (userId != null) {
                        // Menyimpan informasi user ke dalam Firebase Database
                        mDatabase.child("users").child(userId).setValue(userData)
                            .addOnSuccessListener {
                                // Regist berhasil
                                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                                // Pindah ke halaman login atau halaman beranda setelah registrasi berhasil
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish() // Mengakhiri aktivitas registrasi
                            }
                            .addOnFailureListener { e ->
                                // Gagal menyimpan data pengguna
                                Toast.makeText(this, "Gagal menyimpan data pengguna: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Terjadi kesalahan saat mendaftarkan pengguna
                    Toast.makeText(this, "Gagal mendaftarkan pengguna: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }


//        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://identity-dbb35-default-rtdb.firebaseio.com/")


//        btnRegist.setOnClickListener {
//            val emailUser = email.text.toString()
//            val userName = username.text.toString()
//            val passwordUser = password.text.toString()
//
//            if (emailUser.isEmpty() || userName.isEmpty() || passwordUser.isEmpty()) {
//                Toast.makeText(this, "Data Masih Ada Yang Kosong!!!", Toast.LENGTH_SHORT).show()
//            } else {
//                val databaseReference = FirebaseDatabase.getInstance().getReference("users")
//                databaseReference.child(emailUser).child("email").setValue(emailUser)
//                databaseReference.child(userName).child("username").setValue(userName)
//                databaseReference.child(passwordUser).child("password").setValue(passwordUser)
//
//                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
//                val register = Intent(this, LoginActivity::class.java)
//                startActivity(register)
//            }
//        }
    }
}