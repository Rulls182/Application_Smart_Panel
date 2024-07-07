package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        // Inisialisasi CardView
        val cardViewRoom1 = findViewById<CardView>(R.id.room1)
        val cardViewRoom2 = findViewById<CardView>(R.id.room2)
        val cardViewRoom3 = findViewById<CardView>(R.id.room3)
        val cardViewRoom4 = findViewById<CardView>(R.id.room4)

        // Set OnClickListener untuk masing-masing CardView
        cardViewRoom1.setOnClickListener {
            val intent = Intent(this, LampActivity::class.java)
            startActivity(intent)
        }

        cardViewRoom2.setOnClickListener {
            val intent = Intent(this, LampActivity::class.java)
            startActivity(intent)
        }

        cardViewRoom3.setOnClickListener {
            val intent = Intent(this, LampActivity::class.java)
            startActivity(intent)
        }

        cardViewRoom4.setOnClickListener {
            val intent = Intent(this, LampActivity::class.java)
            startActivity(intent)
        }

        // Menghubungkan BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile ->  {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.home ->  {
                    // Jangan buat Intent baru untuk RoomActivity
                    true
                }

                R.id.logout -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        // Atur item yang terpilih saat pertama kali masuk ke RoomActivity
        bottomNavigationView.selectedItemId = R.id.home
    }
}
