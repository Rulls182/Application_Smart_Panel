package com.example.smartpanel

import ChoosePict
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var choosePhotoButton: ImageButton
    private lateinit var choosePict: ChoosePict

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profile_image)
        choosePhotoButton = findViewById(R.id.button_choose_photo)

        choosePict = ChoosePict(this, profileImage, choosePhotoButton)


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile ->  {
                    true
                }

                R.id.home ->  {
                    val intent = Intent(this, RoomActivity::class.java)
                    startActivity(intent)
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
        bottomNavigationView.selectedItemId = R.id.profile
    }

    // Menangani hasil dari memilih foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        choosePict.onActivityResult(requestCode, resultCode, data)
    }
}




