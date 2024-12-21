package com.example.ecta_ex03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("usuarios")
    var usuarioValido = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener {

            val usuarioIngresado = etUsuario.text.toString().trim()
            val claveIngresada = etPassword.text.toString().trim()
            if (usuarioIngresado.isEmpty() || claveIngresada.isEmpty()) {
                Toast.makeText(this, "Error: Alguno de los campos está vacío", Toast.LENGTH_SHORT).show()
            } else {
                myRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val usuariosSnapshot = task.result

                        // Verificamos si la base de datos tiene usuarios
                        if (usuariosSnapshot != null) {
                            usuarioValido = false
                        }

                        // Recorrer todos los usuarios de la base de datos
                        for (usuariosSnapshot in usuariosSnapshot.children) {
                            val usuario = usuariosSnapshot.child("username").getValue(String::class.java)
                            val password = usuariosSnapshot.child("password").getValue(String::class.java)

                            // Comparamos
                            if (usuario == usuarioIngresado && password == claveIngresada) {
                                usuarioValido = true
                                break
                            }
                        }
                        if (usuarioValido) {
                            val intentChat = Intent(this, ChatsActivity::class.java)
                            intentChat.putExtra("username", usuarioIngresado)
                            startActivity(intentChat)
                        } else {
                            Toast.makeText(this, "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Ha ocurrido un error, intente de nuevo mas tarde", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnRegistrarse.setOnClickListener {
            val intentRegistrarse = Intent(this, RegistroActivity::class.java)
            startActivity(intentRegistrarse)
        }
    }
}