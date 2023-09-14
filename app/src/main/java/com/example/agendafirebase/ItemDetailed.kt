package com.example.agendafirebase

import android.content.ContentValues
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class ItemDetailed : AppCompatActivity() {

    lateinit var etxtName: EditText
    lateinit var etxtPhone: EditText
    lateinit var etxtEmail: EditText
    lateinit var btnEditar: Button
    lateinit var btnBorrar: Button
    lateinit var btnGuardar: Button
    //Conexion con SQLite
    val dbHelper = SQLiteHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detailed)

        etxtName = findViewById(R.id.etxtName)
        etxtPhone  = findViewById(R.id.etxtPhone)
        etxtEmail  = findViewById(R.id.etxtEmail)
        btnEditar  = findViewById(R.id.btnEditar)
        btnBorrar = findViewById(R.id.btnBorrar)
        btnGuardar = findViewById(R.id.btnGuardar)

        //Ocultar el boton guardar
        btnGuardar.visibility = View.GONE

        //Se obtiene el mode
        val mode = intent.getStringExtra("mode")
        //Se inicia el item con los itemes necesarios
        val item = intent.getParcelableExtra<Item>("item")

        //Evalua si es una nueva entrada o no
        if (mode == "new"){
            StartNew()
        } else{
            Start(item)
        }

        //Boton Editar
        btnEditar.setOnClickListener {
            Edit(item)
        }
        //Boton Borrar
        btnBorrar.setOnClickListener {
            Borrar(item)
            onBackPressed()
        }
        //Boton Guardar
        btnGuardar.setOnClickListener {
            Guardar(mode, item)
            onBackPressed()
        }
        }
    //Se usa cuando es nuevo registro
    fun StartNew(){
        btnEditar.visibility = View.GONE
        btnBorrar.visibility = View.GONE
        btnGuardar.visibility = View.VISIBLE
    }

    //Cuando es un registro existente
    fun Start(item: Item?){
        //Se hacen no editables
        etxtName.isEnabled = false
        etxtPhone.isEnabled = false
        etxtEmail.isEnabled = false

        //Se asigna el intnet

        if (item != null){
            etxtName.setText(item.nombre)
            etxtPhone.setText(item.telefono)
            etxtEmail.setText(item.correo)
        }
    }

    fun Edit(item: Item?){
        etxtName.isEnabled = true
        etxtPhone.isEnabled = true
        etxtEmail.isEnabled = true
        btnGuardar.visibility = View.VISIBLE
        btnEditar.visibility = View.GONE
        btnBorrar.visibility = View.GONE

    }

    fun Borrar(item: Item?){
        if (item?.id != null){
            //Se le informa el contexto a la db
            val db = dbHelper.writableDatabase
            //Variable que contiene la condicion
            val where = "id=?"
            //variable que guarda el id
            val whereArgs = arrayOf(item.id.toString())
            //Se mand allamar el delete con los parametros deseados
            db.delete("data", where, whereArgs)
            //Se cierra la conexion
            db.close()
        }
        onBackPressed()
    }

    fun Guardar(mode: String?, item: Item?){
        val db = dbHelper.writableDatabase
        val data = ContentValues().apply {
            put("name", etxtName.text.toString())
            put("phone", etxtPhone.text.toString())
            put("email", etxtEmail.text.toString())
        }
        //Verifica si es un nueov registro o un registro existente
        if (mode == "new"){
            db.insert("data", null, data)
        } else{
            val where = "id=?"
            val whereArg = arrayOf(item?.id.toString())
            db.update("data", data, where, whereArg)
        }
        db.close()

    }

}