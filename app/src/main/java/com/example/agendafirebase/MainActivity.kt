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
            itemAdapter = ItemAdapter(itemList)
            recyclerView.adapter = itemAdapter

            //datos de firestores
            db.collection("users")
                .addSnapshotListener{snapshots, error ->
                    if (error != null){
                        Log.w(TAG, "Error al escuchar los cambios", error)
                        return@addSnapshotListener
                    }
                    itemList.clear()
                    for (document in snapshots!!){
                        val item  = Item(
                            id = document.id,
                            nombre = document.getString("name"),
                            telefono = document.getString("phone"),
                            correo = document.getString("email")
                        )
                        itemList.add(item)
                    }
                    itemAdapter.notifyDataSetChanged()

                    //Proporciona el intent a la activity deseada
                    itemAdapter.onItemClick = {
                        val intent = Intent(this, ItemDetailed::class.java)
                        intent.putExtra("item", it)
                        intent. putExtra("mode", "edit")
                        startActivity(intent)
                }
        }

    }

    fun Agregar(){
        val intent = Intent(this, ItemDetailed::class.java)
        intent.putExtra("mode", "new")
        startActivity(intent)
    }
}