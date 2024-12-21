package com.example.ecta_ex03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecta_ex03.adapters.ChatsAdapter
import com.example.ecta_ex03.models.ChatItem
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ChatsActivity : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("usuarios")
    private lateinit var adapter: ChatsAdapter
    val lista1 = recuperarListaUsuarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                initRecyclerView()
                // Puedo actualizar el adaptador de un recycler view
                // Listado de usuarios, empleados, mensajes, etc
                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewChats)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatsAdapter(lista1)
        val usuarioLogueado = intent.getStringExtra("username")
        adapter.setOnItemClickListener(object: ChatsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                startActivity(intentChat().putExtra("usuario_destino", lista1[position].nombre).putExtra("usuario_origen", usuarioLogueado))
            }
        })
        recyclerView.adapter = adapter
    }

    fun recuperarListaUsuarios(): List<ChatItem>{

        val listaUsuarios: MutableList<ChatItem> = mutableListOf()

        myRef.get().addOnCompleteListener { task->
            if(task.isSuccessful) {
                val usuariosSnapshot = task.result
                var cadena = ""
                if(usuariosSnapshot != null) {
                     for (usuarioSnapshot in usuariosSnapshot.children) {
                        val usuario = usuarioSnapshot.child("username").getValue(String::class.java)
                         val usuarioLogueado = intent.getStringExtra("username")
                         if (usuario == usuarioLogueado) {
                             cadena = "(Tú) "
                         } else {
                             cadena = ""
                         }
                         listaUsuarios.add(ChatItem(nombre = cadena + usuario.toString(), imagen = "keloqk", ult_mensaje = "ult.mensaje", ult_conexion = "Hace ${(1..60).random()} minutos"))
                     }
                } else {
                    Log.d("Error", "Base de datos vacía")
                }
            }
        }

        return listaUsuarios
    }

    fun intentChat(): Intent {
        val intentChat = Intent(this, ChatActivity::class.java)
        return intentChat
    }
}