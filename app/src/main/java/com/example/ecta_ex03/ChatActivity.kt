package com.example.ecta_ex03

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecta_ex03.adapters.ChatAdapter
import com.example.ecta_ex03.data.MensajeDB
import com.example.ecta_ex03.models.Mensaje
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ChatActivity : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("mensajes")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre_destino = intent.getStringExtra("usuario_destino")
        val nombre_origen = intent.getStringExtra("usuario_origen")

        val etMensaje = findViewById<EditText>(R.id.messageInput)
        val btnEnviar = findViewById<ImageView>(R.id.btnSend)

        btnEnviar.setOnClickListener {
            val contenido = etMensaje.text.toString()
            enviarMensaje(contenido)
        }

        val btnRegresar = findViewById<ImageView>(R.id.btnBack)
        btnRegresar.setOnClickListener {
            //startActivity(intentChats())
            finish()
        }


        val tvNombre = findViewById<TextView>(R.id.contactName)


        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvNombre.setText(nombre_destino)
                // Podemos actualizar un list view
                val listaMensajes = mutableListOf<Mensaje>()
                listaMensajes.clear()
                for (mensajesSnapshot in snapshot.children) {
                    val mensaje = mensajesSnapshot.getValue(MensajeDB::class.java)

                        if (mensaje != null) {
                            if (mensaje?.remitente == nombre_origen) {
                                listaMensajes.add(Mensaje(tipo = 1, mensaje = mensaje?.contenido.toString()))
                            } else {
                                listaMensajes.add(Mensaje(tipo = 2, mensaje = mensaje?.contenido.toString()))
                            }
                        }



                    // Puedo actualizar el adaptador de un recycler view
                    // Listado de usuarios, empleados, mensajes, etc
                    initRecyclerView(listaMensajes)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun intentChats(): Intent {
        val intentChats = Intent(this, ChatsActivity::class.java)
        return intentChats
    }

    fun initRecyclerView(listaMensajes: List<Mensaje>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMensajes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChatAdapter(listaMensajes)
    }

    fun enviarMensaje(contenido: String) {
        val nombre_destino = intent.getStringExtra("usuario_destino")
        val nombre_origen = intent.getStringExtra("usuario_origen")
        val mensajeid = myRef.push().key!!
        val mensaje = MensajeDB(mensaje_id = mensajeid, remitente = nombre_origen, destinatario = nombre_destino, contenido = contenido)

        myRef.child(mensajeid).setValue(mensaje)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

}
