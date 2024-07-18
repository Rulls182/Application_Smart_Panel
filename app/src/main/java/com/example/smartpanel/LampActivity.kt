package com.example.smartpanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpanel.model.Item
import com.google.firebase.database.*

class LampActivity : AppCompatActivity() {
    private var listView: ListView? = null
    private var items: MutableList<Item>? = null
    private var adapter: CustomAdapter? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lamp)

        // Inisialisasi Firebase database reference
        database = FirebaseDatabase.getInstance().reference

        // Inisialisasi ListView dan data
        listView = findViewById(R.id.list_view)
        items = ArrayList()
        items!!.add(Item("Lampu 1", ""))

        // Mengatur adapter
        adapter = CustomAdapter(this, items as ArrayList<Item>)
        listView?.adapter = adapter

        listView?.setOnItemClickListener { _, _, position, _ ->
            val selectedItem: Item = items!![position]
            Toast.makeText(this@LampActivity, selectedItem.title, Toast.LENGTH_SHORT).show()
        }

        // Menarik data dari cloud
        database.child("sensors/temp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temp = dataSnapshot.getValue(Int::class.java) ?: 0
                findViewById<TextView>(R.id.celcius).text = "$temp°C"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })

        database.child("sensors/power_usage").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val powerUsage = dataSnapshot.getValue(Int::class.java) ?: 0
                findViewById<TextView>(R.id.volt).text = "$powerUsage V"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })

        // Lamp control switch
        val switchLamp = findViewById<Switch>(R.id.switchlamp)
        switchLamp?.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            database.child("devices/lamp").setValue(status)
        }

        // Temperature control switch
        val switchTemp = findViewById<Switch>(R.id.switchtemp)
        switchTemp?.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            database.child("devices/temp_sensor").setValue(status)
        }

        val switchRelay = findViewById<Switch>(R.id.switchrelay)
        switchTemp?.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "ON" else "OFF"
            database.child("devices/relay").setValue(status)
        }

        // button back
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this@LampActivity, RoomActivity::class.java)
            startActivity(intent)
        }
    }
    
}
