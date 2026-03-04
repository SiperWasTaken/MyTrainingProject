package com.example.trainingproject

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

// Fragment per il timer con conto alla rovescia
class Timer : Fragment() {

    // Variabili per gestire il timer
    private lateinit var timerDisplay: TextView
    private var countDownTimer: CountDownTimer? = null // Variabile per il timer
    private var timeRemainingInMillis: Long = 0 // Variabile per il tempo rimanente
    private var isTimerRunning = false
    private lateinit var btnAvvia:Button
    private lateinit var btnReset:Button
    private lateinit var pickerMinutes:NumberPicker
    private lateinit var pickerSeconds:NumberPicker
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_timer, container, false)

        timerDisplay = view.findViewById(R.id.timer_display)
        pickerMinutes = view.findViewById(R.id.picker_minutes)
        pickerSeconds = view.findViewById(R.id.picker_seconds)
        timerDisplay.visibility = TextView.GONE

        pickerSeconds.minValue = 0
        pickerSeconds.maxValue = 59
        pickerMinutes.minValue = 0
        pickerMinutes.maxValue = 59

        pickerMinutes.setOnValueChangedListener { _, _, newVal ->
            Toast.makeText(requireContext(), "Minuti selezionati: $newVal", Toast.LENGTH_SHORT).show()
        }
        pickerSeconds.setOnValueChangedListener { _, _, newVal ->
            Toast.makeText(requireContext(), "Secondi selezionati: $newVal", Toast.LENGTH_SHORT).show()
        }

        // Bottone per avviare/resettare il timer
        btnAvvia= view.findViewById(R.id.avvia)
        btnReset = view.findViewById(R.id.reset)

        btnAvvia.setOnClickListener {

            if (btnAvvia.text == "Start") {

                val minutes = pickerMinutes.value
                val seconds = pickerSeconds.value

                val totalTimeInMillis = (minutes * 60 + seconds) * 1000L

                if(totalTimeInMillis != 0.toLong()){

                    btnAvvia.text = "Pause"
                    btnReset.isEnabled = true
                    btnReset.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

                    timerDisplay.visibility = TextView.VISIBLE
                    pickerSeconds.visibility = NumberPicker.GONE
                    pickerMinutes.visibility = NumberPicker.GONE

                    startTimer(totalTimeInMillis + 999)
                }

            } else if (btnAvvia.text == "Pause") {

                btnAvvia.text = "Restart"
                pauseTimer()

            } else if (btnAvvia.text == "Restart") {

                btnAvvia.text = "Pause"
                startTimer(timeRemainingInMillis)

            }
        }

        btnReset.setOnClickListener {
            resetTimer()
        }

        return view
    }


    // Avvia il timer con il tempo specificato
    private fun startTimer(timeInMillis: Long) {
        timeRemainingInMillis = timeInMillis

        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingInMillis = millisUntilFinished // Aggiorna il tempo rimanente
                val minutes = ((millisUntilFinished % 3600000) / 60000).toInt()
                val seconds = ((millisUntilFinished % 60000) / 1000).toInt()


                // Aggiorna il tempo rimanente
                val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
                timerDisplay.text = timeLeftFormatted


                // Gestione dei suoni
                if ((seconds == 2 || seconds == 1)&& minutes == 0) {

                    playSound()

                }else if(seconds == 0 && minutes == 0){

                    btnAvvia.isEnabled = false
                    playEndSound()

                }
            }

            override fun onFinish() {

            }
        }.start()
        isTimerRunning = true
    }


    // Riproduce un suono di avviso
    private fun playSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.beep)
        mediaPlayer?.start()
    }

    // Riproduce il suono finale in loop
    private fun playEndSound(){
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.beep)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
    }

    private fun stopTimer() {
        if (isTimerRunning) {
            countDownTimer?.cancel()
            isTimerRunning = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (isTimerRunning) {
            stopTimer()
        }
        mediaPlayer?.release()
    }

    // Resetta il timer allo stato iniziale
    private fun resetTimer() {
        countDownTimer?.cancel()
        timeRemainingInMillis = 0
        timerDisplay.text = "00:00"
        btnReset.isEnabled = false
        btnReset.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
        btnAvvia.isEnabled = true
        btnAvvia.text = "Start"

        timerDisplay.visibility = TextView.GONE
        pickerMinutes.visibility = NumberPicker.VISIBLE
        pickerSeconds.visibility = NumberPicker.VISIBLE

        mediaPlayer?.isLooping = false
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}