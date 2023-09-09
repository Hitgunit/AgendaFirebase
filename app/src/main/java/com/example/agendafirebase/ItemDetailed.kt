package com.example.agendafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class ItemDetailed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detailed)

        val item = intent.getParcelableExtra<Item>("item")
        if (item != null){
            val etxtName: EditText = findViewById(R.id.etxtName)
            val etxtPhone: EditText = findViewById(R.id.etxtPhone)
            val etxtEmail: EditText = findViewById(R.id.etxtEmail)

            etxtName.setText(item.nombre)
            etxtPhone.setText(item.telefono)
            etxtEmail.setText(item.correo)
        }
    }
}