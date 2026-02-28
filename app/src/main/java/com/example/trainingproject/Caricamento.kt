package com.example.trainingproject

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trainingproject.AdapterListView.GestoreSchermo
import kotlin.math.sqrt

class Caricamento : AppCompatActivity() {

    private lateinit var titoloApp:TextView
    private lateinit var sottotitolo:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_caricamento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<View>(R.id.fullScreenView).setOnClickListener {
            val intent = Intent(this, Home::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        titoloApp = findViewById<TextView>(R.id.titoloApp)
        sottotitolo = findViewById<TextView>(R.id.flashingIcon)


        setIntches()
        setAnimazioni()

    }

    fun setIntches(){
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        // Calcola la diagonale dello schermo in pollici
        val widthInches = metrics.widthPixels / metrics.xdpi
        val heightInches = metrics.heightPixels / metrics.ydpi
        val screenInches = sqrt(widthInches * widthInches + heightInches * heightInches)

        val gestoreSchermo = GestoreSchermo()
        gestoreSchermo.setDim(screenInches)

        titoloApp.textSize =  gestoreSchermo.getDim() * 20
        sottotitolo.textSize = gestoreSchermo.getDim() * 3
    }

    fun setAnimazioni(){

        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink_animation)
        sottotitolo.startAnimation(blinkAnimation)

        val animation = AnimationUtils.loadAnimation(this, R.anim.title)
        titoloApp.startAnimation(animation)
    }
}

