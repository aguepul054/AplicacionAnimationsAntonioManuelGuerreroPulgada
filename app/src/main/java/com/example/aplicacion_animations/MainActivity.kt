package com.example.aplicacion_animations

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
//FALTA POR MEJORAR
class MainActivity : AppCompatActivity() {

    // Declaración de las variables que contienen las vistas (imágenes y botones) y las animaciones.
    private lateinit var animatedVector: AnimatedVectorDrawableCompat // Animación vectorial de la nave espacial.
    private lateinit var space_ship: ImageView // Imagen de la nave espacial.
    private lateinit var animationDrawable: AnimationDrawable // Animación de la bruja en diferentes acciones.
    private lateinit var animationAttackDrawable: AnimationDrawable // Animación del ataque de la bruja.
    private lateinit var witch: ImageView // Imagen de la bruja.
    private lateinit var buttonAttack: Button // Botón para el ataque.
    private lateinit var buttonRight: Button // Botón para mover a la derecha.
    private lateinit var buttonLeft: Button // Botón para mover a la izquierda.
    private lateinit var buttonCharge: Button // Botón para cargar.

    // Handler para realizar tareas repetidas o con retraso.
    private val handler = Handler()

    // Variables para controlar el estado de movimiento de la bruja (izquierda y derecha).
    private var isMovingLeft = false
    private var isMovingRight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComp() // Inicializa las vistas y las animaciones.
        addListeners() // Agrega los listeners a los botones.
    }

    // Método que inicializa las vistas y las animaciones.
    private fun initComp() {
        // Inicialización de la bruja con una animación inicial.
        witch = findViewById<ImageView>(R.id.witch).apply {
            setBackgroundResource(R.drawable.animacion) // Se asigna una animación a la bruja.
            animationDrawable = background as AnimationDrawable // Se convierte a AnimationDrawable.
            animationDrawable.start() // Se inicia la animación.
        }

        // Inicialización de la nave espacial con una animación vectorial.
        space_ship = findViewById(R.id.space_ship)
        animatedVector = AnimatedVectorDrawableCompat.create(this, R.drawable.animacion_horizontal)!!
        space_ship.setImageDrawable(animatedVector) // Se asigna la animación vectorial a la nave.

        // Inicialización de los botones.
        buttonRight = findViewById(R.id.btnMoveRight)
        buttonLeft = findViewById(R.id.btnMoveLeft)
        buttonAttack = findViewById(R.id.btnAttack)
        buttonCharge = findViewById(R.id.btnCharge)
    }

    // Método que inicia el movimiento repetido del personaje (bruja) hacia la izquierda o la derecha.
    private fun startMovingCharacter() {
        // Usa un Runnable para mover el personaje de forma repetida.
        handler.post(object : Runnable {
            override fun run() {
                if (isMovingLeft) {
                    moveCharacterLeft() // Si está en movimiento hacia la izquierda, se mueve.
                }
                if (isMovingRight) {
                    moveCharacterRight() // Si está en movimiento hacia la derecha, se mueve.
                }
                handler.postDelayed(this, 100) // Repite el movimiento cada 100 milisegundos.
            }
        })
    }

    // Mueve a la bruja hacia la derecha si es posible.
    private fun moveCharacterRight() {
        val currentX = witch.x // Obtiene la posición actual de la bruja en el eje X.
        val screenWidth = resources.displayMetrics.widthPixels // Obtiene el ancho de la pantalla.
        val characterWidth = witch.width // Obtiene el ancho de la bruja.

        // Si la bruja no se sale del borde derecho de la pantalla, la mueve.
        if (currentX + characterWidth + 5 <= screenWidth) {
            witch.x = currentX + 5 // Mueve la bruja 5 píxeles a la derecha.
        }
    }

    // Mueve a la bruja hacia la izquierda si es posible.
    private fun moveCharacterLeft() {
        val currentX = witch.x // Obtiene la posición actual de la bruja en el eje X.

        // Si la bruja no se sale del borde izquierdo de la pantalla, la mueve.
        if (currentX - 5 >= 0) {
            witch.x = currentX - 5 // Mueve la bruja 5 píxeles a la izquierda.
        }
    }

    // Método para agregar los listeners a los botones.
    @SuppressLint("ClickableViewAccessibility")
    private fun addListeners() {

        // Listener para el botón de ataque.
        buttonAttack.setOnClickListener {
            // Cambia la animación de la bruja a la animación de ataque.
            witch.setBackgroundResource(R.drawable.animacion_attack)
            animationAttackDrawable = witch.background as AnimationDrawable
            animationAttackDrawable.start() // Inicia la animación de ataque.
        }

        // Listener para el botón de mover a la derecha, usa onTouchListener para mantener el movimiento mientras el botón está presionado.
        buttonRight.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isMovingRight = true // Inicia el movimiento hacia la derecha.
                    witch.setBackgroundResource(R.drawable.animacion_run) // Cambia la animación de la bruja a "correr".
                    animationDrawable = witch.background as AnimationDrawable
                    animationDrawable.start() // Inicia la animación de correr.

                    startMovingCharacter() // Comienza el movimiento repetido hacia la derecha.
                    true
                }
                MotionEvent.ACTION_UP -> {
                    isMovingRight = false // Detiene el movimiento hacia la derecha cuando se deja de presionar el botón.
                    true
                }
                else -> false
            }
        }

        // Listener para el botón de mover a la izquierda, usa onTouchListener para mantener el movimiento mientras el botón está presionado.
        buttonLeft.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isMovingLeft = true // Inicia el movimiento hacia la izquierda.
                    witch.setBackgroundResource(R.drawable.animacion_run) // Cambia la animación de la bruja a "correr".
                    animationDrawable = witch.background as AnimationDrawable
                    animationDrawable.start() // Inicia la animación de correr.

                    startMovingCharacter() // Comienza el movimiento repetido hacia la izquierda.
                    true
                }
                MotionEvent.ACTION_UP -> {
                    isMovingLeft = false // Detiene el movimiento hacia la izquierda cuando se deja de presionar el botón.
                    true
                }
                else -> false
            }
        }

        // Listener para el botón de carga, cambia la animación a la animación de "cargar".
        buttonCharge.setOnClickListener {
            witch.setBackgroundResource(R.drawable.animacion_charge)
            animationDrawable = witch.background as AnimationDrawable
            animationDrawable.start() // Inicia la animación de cargar.
        }

        // Listener para la nave espacial, al hacer clic se inicia la animación horizontal.
        space_ship.setOnClickListener {
            val animator = AnimatorInflater.loadAnimator(this, R.animator.animacion_horizontal)
            animator.setTarget(space_ship) // Aplica el animador a la nave espacial.
            animator.start() // Inicia la animación de la nave.
        }
    }
}
