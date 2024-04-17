package com.example.smartpanel

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LampActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lamp)

        val switchLamp: Switch = findViewById(R.id.switchlamp)
        val imageLamp: ImageView = findViewById(R.id.iconlamp)

        switchLamp.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch is on, turn the lamp on
                imageLamp.setImageResource(R.drawable.icon_lamp_on)
            } else {
                // Switch is off, turn the lamp off
                imageLamp.setImageResource(R.drawable.icon_lamp_off)
            }
        }
    }

}