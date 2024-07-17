package com.example.smartpanel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.example.smartpanel.model.Item
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(context: Context, items: List<Item>) :
    ArrayAdapter<Item>(context, 0, items) {

    private val items: List<Item> = items
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        // Mendapatkan data item untuk posisi ini
        val item = getItem(position)

        // Melihat view untuk pengisian data
        val itemTitle = convertView!!.findViewById<TextView>(R.id.textcard1)
        val itemIcon = convertView.findViewById<ImageView>(R.id.iconlamp)
        val itemSwitch = convertView.findViewById<Switch>(R.id.switchlamp)

        // Mengisi data ke dalam view template menggunakan objek data
        item?.let {
            itemTitle.text = it.title

            // Mengatur listener untuk switch
            itemSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    itemIcon.setImageResource(R.drawable.icon_lamp_on)
                    database.child("devices/${it.title.toLowerCase().replace(" ", "_")}").setValue("ON")
                } else {
                    itemIcon.setImageResource(R.drawable.icon_lamp_off)
                    database.child("devices/${it.title.toLowerCase().replace(" ", "_")}").setValue("OFF")
                }
            }

            // Menginisialisasi status switch dan ikon berdasarkan data dari Firebase
            database.child("devices/${it.title.toLowerCase().replace(" ", "_")}").get().addOnSuccessListener { dataSnapshot ->
                val status = dataSnapshot.getValue(String::class.java)
                if (status == "ON") {
                    itemSwitch.isChecked = true
                    itemIcon.setImageResource(R.drawable.icon_lamp_on)
                } else {
                    itemSwitch.isChecked = false
                    itemIcon.setImageResource(R.drawable.icon_lamp_off)
                }
            }
        }

        // Mengembalikan view yang sudah selesai untuk ditampilkan di layar
        return convertView
    }
}
