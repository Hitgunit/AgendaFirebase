package com.example.agendafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var itemList: ArrayList<Item>
    lateinit var recyclerView: RecyclerView
    lateinit var itemAdapter: ItemAdapter
    lateinit var btnAgregar: Button

    //Conexion con SQLite
    val dbHelper = SQLiteHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StartRecyclerView()
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            Agregar()
        }

        }

    override fun onResume() {
        super.onResume()
        UpdateView()
    }

        fun StartRecyclerView(){
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)

            //Se obtiene los datos
            val itemList = getData()

            itemAdapter = ItemAdapter(itemList)
            recyclerView.adapter = itemAdapter

            //Proporciona el intent a la activity deseada
            itemAdapter.onItemClick = {
                val intent = Intent(this, ItemDetailed::class.java)
                intent.putExtra("item", it)
                startActivity(intent)
        }

    }

    fun UpdateView(){
        val newItem = getData()
        itemAdapter.itemList = newItem
        itemAdapter.notifyDataSetChanged()
    }

    fun getData(): ArrayList<Item>{
        val itemList = ArrayList<Item>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM data", null)

        if (cursor.moveToFirst()){
            do{
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                val email = cursor.getString(3)

                itemList.add(Item(name, phone, email))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return itemList
    }

    fun Agregar(){
        val intent = Intent(this, ItemDetailed::class.java)
        intent.putExtra("mode", "new")
        startActivity(intent)
    }
}