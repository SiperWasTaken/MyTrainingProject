package com.example.trainingproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AggiungiEsercizio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aggiungi_esercizio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        switchSetup()
    }

    private fun switchSetup(){

        val switchEsecuzione = findViewById<Switch>(R.id.tempoEsecuzioneSwitch)
        val minutiEsecuzione = findViewById<EditText>(R.id.minutiEsecuzione)
        val secondiEsecuzione = findViewById<EditText>(R.id.secondiEsecuzione)
        val tempoEsecuzioneText = findViewById<TextView>(R.id.tempoEsecuzioneText)

        switchEsecuzione.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tempoEsecuzioneText.text = "Tempo"
                minutiEsecuzione.visibility = View.VISIBLE
                secondiEsecuzione.visibility = View.VISIBLE
                minutiEsecuzione.isEnabled = true
                secondiEsecuzione.isEnabled = true
                minutiEsecuzione.hint = "0"
                secondiEsecuzione.hint = "0"
            } else {
                tempoEsecuzioneText.text = " "
                minutiEsecuzione.visibility = View.GONE
                secondiEsecuzione.visibility = View.GONE
                minutiEsecuzione.isEnabled = false
                secondiEsecuzione.isEnabled = false
                minutiEsecuzione.hint = ""
                secondiEsecuzione.hint = ""
            }
        }

        tempoEsecuzioneText.text = " "
        minutiEsecuzione.visibility = View.GONE
        secondiEsecuzione.visibility = View.GONE
        minutiEsecuzione.isEnabled = false
        secondiEsecuzione.isEnabled = false

    }

    fun passaDatiAggiungiScheda(view: View){

        val nomeEsercizioText = findViewById<EditText>(R.id.nomeEsercizio)
        val numeroSetText = findViewById<EditText>(R.id.numeroSet)
        val minutiRecuperoText = findViewById<EditText>(R.id.minutiRecupero)
        val secondiRecuperoText = findViewById<EditText>(R.id.secondiRecupero)
        val minutiEsecuzioneText = findViewById<EditText>(R.id.minutiEsecuzione)
        val secondiEsecuzioneText = findViewById<EditText>(R.id.secondiEsecuzione)

        val nomeEsercizio = nomeEsercizioText.text.toString()
        var numeroSet = numeroSetText.text.toString().toIntOrNull() ?: 0
        var recupero = ((minutiRecuperoText.text.toString().toIntOrNull()?: 0) * 60) + (secondiRecuperoText.text.toString().toIntOrNull()?:0)
        var tempoEsecuzione = 0

        if(isStringEmpty(nomeEsercizio)){
            Toast.makeText(this,"Inserire il nome dell'esercizio.", Toast.LENGTH_SHORT).show()
        }else if(numeroSet == 0){
            Toast.makeText(this,"Inserire il numero di set.", Toast.LENGTH_SHORT).show()
        }else if(recupero == 0){
            Toast.makeText(this,"Inserire il tempo di recupero tra gli esercizi.", Toast.LENGTH_SHORT).show()
        }else{

            if(minutiEsecuzioneText.isEnabled && secondiEsecuzioneText.isEnabled){
                tempoEsecuzione = ((minutiEsecuzioneText.text.toString().toIntOrNull() ?: 0) * 60) + (secondiEsecuzioneText.text.toString().toIntOrNull()?:0)
            }else{
                tempoEsecuzione = 0
            }

            Toast.makeText(this,"$nomeEsercizio, $numeroSet, $recupero, $tempoEsecuzione", Toast.LENGTH_SHORT).show()

            val resultIntent = Intent()
            resultIntent.putExtra("chiave", "DATI DA PASSARE")
            resultIntent.putExtra("nomeEsercizio", nomeEsercizio)
            resultIntent.putExtra("numeroSet", numeroSet)
            resultIntent.putExtra("recupero", recupero)
            resultIntent.putExtra("esecuzione", tempoEsecuzione)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }


    private fun isStringEmpty(input: String): Boolean {
        return input.replace(" ", "").isBlank()
    }

    fun backAggiungiScheda(view: View){
        finish()
    }
}