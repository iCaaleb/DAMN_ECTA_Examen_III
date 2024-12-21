package com.example.ecta_ex03.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecta_ex03.R
import com.example.ecta_ex03.models.Mensaje

class ChatAdapter(val listaMensajes: List<Mensaje>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class ViewHolder1(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvMensaje = itemView.findViewById<TextView>(R.id.tvMensajeEnviado)

        fun render(mensaje: String) {
            tvMensaje.text = mensaje
        }
    }

    inner class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvMensajeRecibido = itemView.findViewById<TextView>(R.id.tvMensajeRecibido)

        fun render(mensaje: String) {
            tvMensajeRecibido.text = mensaje
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listaMensajes[position].tipo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ViewHolder1(LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje_enviado, parent, false))
            2 -> ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje_recibido, parent, false))
            else -> {throw IllegalArgumentException("Invalido")}
        }
    }

    override fun getItemCount(): Int {
        return listaMensajes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listaMensajes[position]
        when(holder) {
            is ViewHolder1 -> holder.render(item.mensaje)
            is ViewHolder2 -> holder.render(item.mensaje)
        }
    }
}