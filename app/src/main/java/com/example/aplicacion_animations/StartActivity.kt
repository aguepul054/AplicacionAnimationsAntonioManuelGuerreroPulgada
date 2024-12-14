package com.example.aplicacion_animations

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {
    private lateinit var btnStartGame:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicialización de las vistas y componentes (botones).
        initComp()

        // Se agregan los listeners a los botones para gestionar los eventos de clic.
        addListeners()
    }
    // Método para inicializar los componentes (vistas) necesarios para la actividad.
    private fun initComp(){
        // Se asocia el botón "Iniciar juego" (btnStartGame) con el componente correspondiente en el XML.
        btnStartGame = findViewById(R.id.btnStartGame)
    }
    private fun addListeners(){
        btnStartGame.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}

