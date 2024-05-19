package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val buttonLink: Button = findViewById(R.id.btnNext)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        buttonLink.setOnClickListener {
            // Intent untuk membuka aktivitas baru
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


//        var auth: FirebaseAuth = Firebase.auth
//
//        if (auth.currentUser != null){
//            val intentSuhu = Intent(this, LampActivity::class.java)
//            startActivity(intentSuhu)
//        } else{
//            val intentLogin = Intent(this, LoginActivity::class.java)
//            startActivity(intentLogin)
//        }
    }

//    fun NavigateToLogin(view: View) {
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//    }
}