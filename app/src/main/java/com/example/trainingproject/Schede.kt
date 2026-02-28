package com.example.trainingproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.trainingproject.AdapterListView.ListAdapterSchede
import com.example.trainingproject.RoomDatabase.database.AppDatabase
import com.example.trainingproject.RoomDatabase.entities.Scheda
import kotlinx.coroutines.launch

class Schede : Fragment() {

    private lateinit var listView: ListView
    private lateinit var adapter: ListAdapterSchede
    private lateinit var scrittaCentrale: TextView
    private val nomeSchede = mutableListOf<String>()
    private val schedaIds = mutableListOf<Int>()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = AppDatabase.getDatabase(requireContext())

        val view = inflater.inflate(R.layout.fragment_schede, container, false)

        scrittaCentrale = view.findViewById(R.id.textNoSchede)

        val addButton: ImageView = view.findViewById(R.id.addButton)

        listView = view.findViewById(R.id.listViewSchede)

        listView.setOnItemClickListener { _, _, position, _ ->

            val schedaId = schedaIds[position]
            val schedaNome = nomeSchede[position]

            val intent = Intent(requireContext(), EserciziScheda::class.java)
            intent.putExtra("SCHEDA_ID", schedaId)
            intent.putExtra("SCHEDA_NOME", schedaNome)
            startActivity(intent)
        }

        adapter = ListAdapterSchede(
            context = requireContext(),
            resource = R.layout.list_item_schede,
            items = nomeSchede,
            schedaIds = schedaIds,
            onDeleteClick = { schedaId ->

                deleteScheda(schedaId)

            },
            onChangeNameClick = { schedaId ->

                allertModificaScheda(schedaId)  //PASSARE L'ID E CAMBIARE IL NOME NEL DATABASE

            }
        )

        listView.adapter = adapter

        loadSchedeFromDatabase()

        addButton.setOnClickListener {

            val inputEditText = EditText(requireContext())

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Nuova Scheda")
                .setMessage("Inserire il nome della scheda:")
                .setView(inputEditText)
                .setPositiveButton("OK") { _, _ ->

                    val userInput = inputEditText.text.toString()

                    if (userInput.isNotEmpty()) {
                        nomeSchede.add(userInput)
                        insertDatabase(userInput)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Il campo è vuoto! Inserire il nome della scheda.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Annulla", null)
                .create()
            dialog.show()
        }


        return view
    }

    private fun setupScrittaCentrale() {

        if (nomeSchede.isNotEmpty()) {
            scrittaCentrale.visibility = View.GONE
        } else {
            scrittaCentrale.visibility = View.VISIBLE
        }
    }


    private fun loadSchedeFromDatabase() {
        lifecycleScope.launch {

            val schede = database.schedaEsercizioDao().getAllSchede()
            nomeSchede.clear()
            schedaIds.clear()
            nomeSchede.addAll(schede.map { it.nome })
            schedaIds.addAll(schede.map { it.id })
            adapter.notifyDataSetChanged()


            setupScrittaCentrale()
        }
    }

    private fun deleteScheda(schedaId: Int) {

        AlertDialog.Builder(requireContext())
            .setMessage("Eliminare la scheda selezionata?")
            .setCancelable(true)
            .setPositiveButton("Ok") { _, _ ->
                lifecycleScope.launch {

                    database.schedaEsercizioDao().deleteSchedaById(schedaId)

                    val index = schedaIds.indexOf(schedaId)
                    if (index != -1) {
                        nomeSchede.removeAt(index)
                        schedaIds.removeAt(index)
                    }

                    adapter.notifyDataSetChanged()
                    setupScrittaCentrale()
                }
            }
            .setNegativeButton("Annulla", null)
            .show()
    }

    private fun allertModificaScheda(schedaId: Int){

        val inputEditText = EditText(requireContext())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Modifica Scheda")
            .setMessage("Inserire il nome della scheda:")
            .setView(inputEditText)
            .setPositiveButton("OK") { _, _ ->

                val userInput = inputEditText.text.toString()

                if (userInput.isNotEmpty()) {

                    aggiornaNomeScheda(userInput, schedaId)

                } else {
                    Toast.makeText(requireContext(), "Il campo è vuoto! Inserire il nome della scheda.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Annulla", null)
            .create()
        dialog.show()

    }

    private fun aggiornaNomeScheda(userInput : String, schedaId: Int){

        lifecycleScope.launch {

            database.schedaEsercizioDao().aggiornaNomeScheda(schedaId,userInput)

            loadSchedeFromDatabase()
        }

    }


    private fun insertDatabase(nomeScheda: String) {
        if (nomeScheda.isNotEmpty()) {
            lifecycleScope.launch {
                val schedaId = database.schedaEsercizioDao().insertScheda(Scheda(nome = nomeScheda)).toInt()

                nomeSchede.add(nomeScheda)
                schedaIds.add(schedaId)

                loadSchedeFromDatabase()

                Toast.makeText(requireContext(), "Scheda aggiunta con ID: $schedaId", Toast.LENGTH_SHORT).show()
            }

            setupScrittaCentrale()
        }
    }

}