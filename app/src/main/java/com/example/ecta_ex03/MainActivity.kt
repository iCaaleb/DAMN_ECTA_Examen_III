package com.example.ecta_ex03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCreditos = findViewById<Button>(R.id.btnCreditos)
        val btnChat = findViewById<Button>(R.id.btnChat)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnChat.setOnClickListener {
            val intentChat = Intent(this, LoginActivity::class.java)
            startActivity(intentChat)
        }

        btnCreditos.setOnClickListener {
            val intentCreditos = Intent(this, CreditosActivity::class.java)
            startActivity(intentCreditos)
        }

        btnSalir.setOnClickListener {
            finishAffinity()
        }
    }
}