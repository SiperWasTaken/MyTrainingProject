package com.example.trainingproject

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ModificaEsercizio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifica_esercizio)

    }

    fun back(view: View) {

        AlertDialog.Builder(this)
            .setMessage("Annullare le modifiche?")
            .setCancelable(true)
            .setPositiveButton("Ok") { _, _ ->

                finish()

            }
            .setNegativeButton("Annulla", null)
            .show()

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Annullare le modifiche?")
            .setCancelable(true)
            .setPositiveButton("Ok") { _, _ ->

                super.onBackPressed()
                finish()

            }
            .setNegativeButton("Annulla", null)
            .show()
    }
}