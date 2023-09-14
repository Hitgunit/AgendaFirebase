package com.example.agendafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var itemAdapter: ItemAdapter
    lateinit var btnAgregar: Button

    //Conexion con SQLite
    val dbHelper = SQLiteHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Se crea el RecyclerView
        StartRecyclerView()
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            Agregar()
        }

        }

    //cada vez que se resume se actualiza el recyclerView con los registros
    override fun onResume() {
        super.onResume()
        //fun que lo hace
        UpdateView()
    }

        fun StartRecyclerView(){
            //Se busca el recyclerView en XML
            recyclerView = findViewById(R.id.recyclerView)
            //Se indica que todos los items sera iguales
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
        //Se actualiza los datos con los nuevos
        val newItem = getData()
        itemAdapter.itemList = newItem
        itemAdapter.notifyDataSetChanged()
    }

    //Se crea un fun de ArrayList<Item>
    fun getData(): ArrayList<Item>{
        val itemList = ArrayList<Item>()
        //Se usa el dbHelper para darle a entender que ese hara
        val db = dbHelper.readableDatabase
        //Se crea el cursor para darle seguimiento a la lectura de datos
        val cursor = db.rawQuery("SELECT * FROM data", null)
        //Se hace un ciclo con las herramientas de SQLite
        if (cursor.moveToFirst()){
            do{
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                val email = cursor.getString(3)

                itemList.add(Item(id, name, phone, email))
            } while (cursor.moveToNext())
        }
        //Se cierran las conexiones
        cursor.close()
        db.close()

        return itemList
    }

    fun Agregar(){
        //Se manda los valores al intent
        val intent = Intent(this, ItemDetailed::class.java)
        intent.putExtra("mode", "new")
        startActivity(intent)
    }
}