package com.example.ecta_ex03

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecta_ex03.data.Usuario
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RegistroActivity : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val etNombreUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etNombreCompleto = findViewById<EditText>(R.id.etNombreCompleto)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)

        btnRegistrarse.setOnClickListener {
            if (etNombreUsuario.text.toString().isEmpty() || etPassword.text.toString()
                    .isEmpty() || etCorreo.text.toString()
                    .isEmpty() || etNombreCompleto.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Error: Alguno de los campos está vacío", Toast.LENGTH_SHORT)
                    .show()
            } else if (etNombreUsuario.text.toString().length > 10) {
                Toast.makeText(
                    this,
                    "Error: El nombre de usuario debe contener 10 o menos caracteres",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val usuario = Usuario(nombreCompleto = etNombreCompleto.text.toString(),
                    correo = etCorreo.text.toString(),
                    password = etPassword.text.toString(),
                    username = etNombreUsuario.text.toString())
                crearNuevoUsuario(usuario)
            }
        }
    }

    fun crearNuevoUsuario(usuario: Usuario) {
        val idUsuario = myRef.push().key!!
        usuario.idUsuario = idUsuario

        myRef.child(idUsuario).setValue(usuario)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Se ha completado el registro exitosamente. Inicie sesión",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "No se pudo completar el registro. Intente de nuevo mas tarde",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}