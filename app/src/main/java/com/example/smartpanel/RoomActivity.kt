package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
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
//                R.id.profile ->  {
//                    startActivity(Intent(this, MainActivity::class.java))
//                    true
//                }

                R.id.home ->  {
                    startActivity(Intent(this, RoomActivity::class.java))
                    true
                }

                R.id.logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }

                else -> false
            }


        }
    }

}
