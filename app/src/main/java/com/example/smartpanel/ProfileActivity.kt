package com.example.smartpanel

import ChoosePict
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var choosePhotoButton: ImageButton
    private lateinit var editPhotoButton: ImageButton
    private lateinit var emailTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var choosePict: ChoosePict
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profile_image)
        choosePhotoButton = findViewById(R.id.button_choose_photo)
        editPhotoButton = findViewById(R.id.button_edit_photo)
        emailTextView = findViewById(R.id.textEmail)
        usernameTextView = findViewById(R.id.textUsername)

        choosePict = ChoosePict(this, profileImage, choosePhotoButton, editPhotoButton)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> true
                R.id.home -> {
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

        bottomNavigationView.selectedItemId = R.id.profile
        database = FirebaseDatabase.getInstance().reference
        fetchUserData()
    }

    private fun fetchUserData() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", null)

        if (uid != null) {
            database.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val username = snapshot.child("username").getValue(String::class.java)
                        val email = snapshot.child("email").getValue(String::class.java)
                        emailTextView.text = email
                        usernameTextView.text = username
                        Log.d("ProfileActivity", "Username: $username, Email: $email")
                    } else {
                        Log.d("ProfileActivity", "No such document")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ProfileActivity", "get failed with ", error.toException())
                }
            })
        } else {
            Log.d("ProfileActivity", "UID is null")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        choosePict.onActivityResult(requestCode, resultCode, data)
    }
}