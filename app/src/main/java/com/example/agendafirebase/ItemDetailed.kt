package com.example.agendafirebase

import android.content.ContentValues.TAG
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
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

        //se ponen los valores
        val intent = intent.getParcelableExtra<Item>("item")
        etxtName.setText(intent?.nombre)
        etxtPhone.setText(intent?.telefono)
        etxtEmail.setText(intent?.correo)


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
        // Create a new user with a first and last name
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

    }

}