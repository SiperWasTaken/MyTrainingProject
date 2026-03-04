package com.example.trainingproject.AdapterListView


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.trainingproject.R

// Adapter personalizzato per visualizzare gli esercizi in una ListView
class ListAdapterEsercizi(
    context: Context,
    private val dataArrayList: ArrayList<ListData>,
    private val eserciziIds:List<Int>,
    private val onDeleteClick: (Int) -> Unit,
    private val onModificaClick: (Int) -> Unit
) : ArrayAdapter<ListData>(context, R.layout.list_item_esercizi, dataArrayList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val listData = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_esercizi, parent, false)
        }

        val listName = view!!.findViewById<TextView>(R.id.listName)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        val cambiaNomeTextView: TextView = view.findViewById(R.id.modifica)

        listName.text = listData?.name ?: ""


        deleteButton.setOnClickListener {

            onDeleteClick(eserciziIds[position])

        }

        cambiaNomeTextView.setOnClickListener{

            onModificaClick(eserciziIds[position])

        }

        return view
    }
}

