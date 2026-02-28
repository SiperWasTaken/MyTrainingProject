package com.example.trainingproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trainingproject.AdapterListView.ListAdapterEsercizi
import com.example.trainingproject.AdapterListView.ListData
import com.example.trainingproject.RoomDatabase.database.AppDatabase
import com.example.trainingproject.RoomDatabase.entities.Esercizio
import kotlinx.coroutines.launch

class EserciziScheda : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    private var schedaId : Int = 0
    private lateinit var listView: ListView
    private lateinit var adapter: ListAdapterEsercizi
    private val nomeEsercizi = ArrayList<ListData>()
    private val eserciziIds = ArrayList<Int>()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esercizi_scheda)

        val schedaSelezionata = intent.getStringExtra("SCHEDA_NOME")
        val nomeScheda = findViewById<TextView>(R.id.nomeScheda)
        nomeScheda.text = schedaSelezionata

        listView = findViewById(R.id.listViewEsercizi)
        database = AppDatabase.getDatabase(this)

        adapter = ListAdapterEsercizi(this, nomeEsercizi, eserciziIds,

            onDeleteClick = {esercizioId->

                deleteEsercizio(esercizioId)

            }, onModificaClick = {idEsercizio->

                allertModificaEsercizio(idEsercizio)

            })
        listView.adapter = adapter

        schedaId = intent.getIntExtra("SCHEDA_ID", -1)
        if (schedaId != -1) {

            loadEsercizi()

        }else{
            Toast.makeText(this,"ERRORE: id non riconosiuto", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadEsercizi() {

        lifecycleScope.launch {
            val esercizi = database.schedaEsercizioDao().getEserciziByScheda(schedaId)

            nomeEsercizi.clear()
            eserciziIds.clear()

            eserciziIds.addAll(esercizi.map { it.id })

            esercizi.forEach { esercizio ->
                nomeEsercizi.add(ListData(esercizio.nome.toString()))
            }

            adapter.notifyDataSetChanged()
        }
    }

    private fun allertModificaEsercizio(esercizioId: Int){

        val intent = Intent(this, ModificaEsercizio::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }

    private fun aggiornaNomeEsercizio(userInput : String, esercizioId: Int){

        lifecycleScope.launch {

            database.schedaEsercizioDao().aggiornaNomeEsercizio(esercizioId,userInput)

            loadEsercizi()
        }

    }

    private fun deleteEsercizio(esercizioId: Int) {

        AlertDialog.Builder(this)
            .setMessage("Eliminare l'esercizio selezionato?")
            .setCancelable(true)
            .setPositiveButton("Ok") { _, _ ->

                lifecycleScope.launch {

                    database.schedaEsercizioDao().deleteEsercizioById(esercizioId)

                    val index = eserciziIds.indexOf(esercizioId)
                    if (index != -1) {
                        nomeEsercizi.removeAt(index)
                        eserciziIds.removeAt(index)
                    }

                    adapter.notifyDataSetChanged()
                }

            }
            .setNegativeButton("Annulla", null)
            .show()
    }

    fun backHome(view: View) {

        finish()

    }


    fun aggiungiEsercizio(view: View){
        val intent = Intent(this, AggiungiEsercizio::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val valore = data?.getStringExtra("chiave")

            val nomeEsercizio = data?.getStringExtra("nomeEsercizio")
            val numeroSet = data?.getIntExtra("numeroSet", -1)
            val tempoRecupero = data?.getIntExtra("recupero", -1)
            val tempoEsecuzione = data?.getIntExtra("esecuzione", -1)

            if (nomeEsercizio != null && numeroSet != null && tempoRecupero != null && tempoEsecuzione != null) {

                val nuovoEsercizio = Esercizio(
                    nome = nomeEsercizio,
                    numeroSet = numeroSet,
                    secondiRecupero = tempoRecupero,
                    secondiEsecuzione = tempoEsecuzione,
                    schedaId = schedaId
                )

                lifecycleScope.launch {
                    database.schedaEsercizioDao().insertEsercizio(nuovoEsercizio)
                    loadEsercizi()
                }

            }

            Toast.makeText(this,"$valore,$nomeEsercizio, $numeroSet, $tempoRecupero, $tempoEsecuzione", Toast.LENGTH_SHORT).show()
        }
    }
}
