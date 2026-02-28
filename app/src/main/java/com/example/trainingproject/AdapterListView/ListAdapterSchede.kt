package com.example.trainingproject.AdapterListView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.trainingproject.R

class ListAdapterSchede(
    context: Context,
    private val resource: Int,
    private val items: List<String>,
    private val schedaIds: List<Int>,
    private val onDeleteClick: (Int) -> Unit,
    private val onChangeNameClick: (Int) -> Unit,
) : ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val nameTextView: TextView = view.findViewById(R.id.listName)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
        val cambiaNomeTextView: TextView = view.findViewById(R.id.cambiaNomeScheda)

        nameTextView.text = items[position]

        deleteButton.setOnClickListener {
            onDeleteClick(schedaIds[position])
        }

        cambiaNomeTextView.setOnClickListener {
            onChangeNameClick(schedaIds[position])
        }

        return view
    }
}
