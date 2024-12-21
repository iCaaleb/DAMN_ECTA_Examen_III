package com.example.ecta_ex03.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ecta_ex03.R
import com.example.ecta_ex03.models.ChatItem

class ChatsAdapter(val listaChats: List<ChatItem>): RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    inner class ChatsViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val nombreUsuario = itemView.findViewById<TextView>(R.id.nombreUsuario)
        val fotoPerfil = itemView.findViewById<ImageView>(R.id.fotoPerfil)
        val ultimoMensaje = itemView.findViewById<TextView>(R.id.ultimoMensaje)
        val ultimaConexion = itemView.findViewById<TextView>(R.id.ultimaConexion)

        fun render(chat: ChatItem) {
            nombreUsuario.text = chat.nombre
            ultimoMensaje.text = chat.ult_mensaje
            ultimaConexion.text = chat.ult_conexion
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = layoutInflater.inflate(R.layout.item_usuario, parent, false)
        return ChatsViewHolder(itemView, miListener)
    }

    override fun getItemCount(): Int {
        return listaChats.size
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val item = listaChats[position]
        holder.render(item)
    }
}