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

        val mode = intent.getStringExtra("mode")

        //Evalua si es una nueva entrada o no
        if (mode == "new"){
            StartNew()
        } else{
            Start()
        }

        //Boton Editar
        btnEditar.setOnClickListener {
            Edit()
        }
        //Boton Borrar
        btnBorrar.setOnClickListener {
            //Borrar()
            onBackPressed()
        }
        //Boton Guardar
        btnGuardar.setOnClickListener {
            Guardar()
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
    fun Start(){
        //Se hacen no editables
        etxtName.isEnabled = false
        etxtPhone.isEnabled = false
        etxtEmail.isEnabled = false

        //Se asigna el intnet
        val item = intent.getParcelableExtra<Item>("item")
        if (item != null){
            etxtName.setText(item.nombre)
            etxtPhone.setText(item.telefono)
            etxtEmail.setText(item.correo)
        }
    }

    fun Edit(){
        etxtName.isEnabled = true
        etxtPhone.isEnabled = true
        etxtEmail.isEnabled = true
        btnGuardar.visibility = View.VISIBLE
        btnEditar.visibility = View.GONE
        btnBorrar.visibility = View.GONE
    }

    fun Borrar(){

    }

    fun Guardar(){
        val db = dbHelper.writableDatabase
        val data = ContentValues().apply {
            put("name", etxtName.text.toString())
            put("phone", etxtPhone.text.toString())
            put("email", etxtEmail.text.toString())
        }
        val newRowId = db.insert("data", null, data)
    }

}