package com.example.trainingproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.trainingproject.ViewModels.CronometroViewModel

class Cronometro : Fragment() {

    private lateinit var timerDisplay: TextView
    private lateinit var btnStartStop: Button
    private lateinit var btnReset: Button

    private lateinit var listViewParziali: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var listaParziali = ArrayList<String>()

    private val viewModel: CronometroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cronometro, container, false)

        timerDisplay = view.findViewById(R.id.timer_display)
        btnStartStop = view.findViewById(R.id.start_stop_button)
        btnReset = view.findViewById(R.id.reset_button)
        listViewParziali = view.findViewById(R.id.listaParziali)

        setupListView()

        // Osserva il tempo dal ViewModel e aggiorna il display
        viewModel.timeFormatted.observe(viewLifecycleOwner) { time ->
            timerDisplay.text = time

            if(time == "00:00:00"){

                btnStartStop.text = "Start"
                btnReset.text = "Reset"

            }else if(time != "00:00:00" && !viewModel.isRunning()){

                btnStartStop.text = "Start"
                btnReset.text = "Reset"
                btnReset.isEnabled = true

            }else if(viewModel.isRunning()){

                btnStartStop.text = "Stop"
                btnReset.text = "Parziale"
                btnReset.isEnabled = true

            }
        }

        btnStartStop.setOnClickListener {
            if (btnStartStop.text == "Start") {
                viewModel.startTimer()
                btnStartStop.text = "Stop"
                btnReset.text = "Parziale"
                btnReset.isEnabled = true
            } else {
                viewModel.stopTimer()
                btnStartStop.text = "Start"
                btnReset.text = "Reset"
            }
        }

        btnReset.setOnClickListener {
            if (btnReset.text == "Reset") {
                viewModel.resetTimer()
                btnReset.isEnabled = false

                listaParziali = viewModel.clearParziali()
                adapter.notifyDataSetChanged()

            } else {

                listaParziali = viewModel.addParziale(timerDisplay.text.toString())
                adapter.notifyDataSetChanged()

            }
        }

        return view
    }

    private fun setupListView() {

        listaParziali = viewModel.getParziali()

        adapter = ArrayAdapter(requireContext(), R.layout.item_parziale, listaParziali)
        listViewParziali.adapter = adapter
    }
}