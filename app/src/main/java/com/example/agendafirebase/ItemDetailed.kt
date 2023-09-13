package com.example.agendafirebase

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemDetailed : AppCompatActivity() {

    lateinit var etxtName: EditText
    lateinit var etxtPhone: EditText
    lateinit var etxtEmail: EditText
    lateinit var btnEditar: Button
    lateinit var btnBorrar: Button
    lateinit var btnGuardar: Button
    //Conexion a firestore
    val db = Firebase.firestore
    //Identificador de firestore
    var docId: String? = null

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
            Borrar(item?.id)
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

        //se ponen los valores
        etxtName.setText(item?.nombre)
        etxtPhone.setText(item?.telefono)
        etxtEmail.setText(item?.correo)


    }

    fun Edit(item: Item?){
        etxtName.isEnabled = true
        etxtPhone.isEnabled = true
        etxtEmail.isEnabled = true
        btnGuardar.visibility = View.VISIBLE
        btnEditar.visibility = View.GONE
        btnBorrar.visibility = View.GONE

    }

    fun Borrar(id: String?){
        //Si no encuentra nada, no borra nada
        if (id == null){
            return
        } else{

            db.collection("users").document(id)
                .delete()
                .addOnCanceledListener {
                    Log.d(TAG, "Borrado exitosamente")
                }
                .addOnFailureListener{e->
                    Log.w(TAG, "Error al eliminarlo", e)
                }
        }
    }

    fun Guardar(mode: String?, item: Item?){
        // Se crea un usuario nuevo
        if (mode == "new"){
            val user = hashMapOf(
                "name" to etxtName.text.toString(),
                "phone" to etxtPhone.text.toString(),
                "email" to etxtEmail.text.toString()
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
        }else{
            //Guardar cambios de un registro previo
            if (item?.id == null){
                return
            } else{
                //Se crea el mapa de datos
                val datos = hashMapOf(
                    "name" to etxtName.text.toString(),
                    "phone" to etxtPhone.text.toString(),
                    "email" to etxtEmail.text.toString()
                )
                //Se actualiza los datos
                db.collection("users").document(item.id).update(datos as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.d(TAG, "Docuemnto actualizado correctamente")
                    }
                    .addOnFailureListener {
                        Log.w(TAG, "No se pudo actualizar el docuemnto correctamente")
                    }
            }
        }




    }

}