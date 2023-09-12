package com.example.agendafirebase

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var itemList: ArrayList<Item>
    lateinit var recyclerView: RecyclerView
    lateinit var itemAdapter: ItemAdapter
    lateinit var btnAgregar: Button
    //Conexion a firebase
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        StartRecyclerView()
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            Agregar()
        }

        }

        fun StartRecyclerView(){
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)

            itemList = ArrayList()

            itemList.add(Item("Adair", "4521040036", "ada_dp10@hotmail.com"))
            itemList.add(Item("Jose", "4521204043", "jose123@hotmail.com"))
            itemList.add(Item("Maria", "4521597865", "maria@hotmail.com"))

            itemAdapter = ItemAdapter(itemList)
            recyclerView.adapter = itemAdapter

            //Proporciona el intent a la activity deseada
            itemAdapter.onItemClick = {
                val intent = Intent(this, ItemDetailed::class.java)
                intent.putExtra("item", it)
                startActivity(intent)
        }

    }

    fun Agregar(){
        val intent = Intent(this, ItemDetailed::class.java)
        intent.putExtra("mode", "new")
        startActivity(intent)
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}