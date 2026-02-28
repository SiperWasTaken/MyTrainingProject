package com.example.trainingproject.ViewModels
import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.util.Locale

class CronometroViewModel : ViewModel() {

    private val _timeFormatted = MutableLiveData("00:00:00")
    val timeFormatted: LiveData<String> get() = _timeFormatted
    val listaParziali = ArrayList<String>()
    var numParziale = 1

    private var isRunning = false
    private var timeInMillis: Long = 0L
    private var startTime: Long = 0L

    private var job: Job? = null

    fun startTimer() {
        if (isRunning) return

        startTime = System.currentTimeMillis() - timeInMillis
        isRunning = true

        job = viewModelScope.launch(Dispatchers.IO) {
            while (isRunning) {
                timeInMillis = System.currentTimeMillis() - startTime
                val formattedTime = formatTime(timeInMillis)

                withContext(Dispatchers.Main) {
                    _timeFormatted.value = formattedTime
                }
                delay(9) // Aggiorna ogni 10 ms
            }
        }
    }

    fun stopTimer() {
        isRunning = false
        job?.cancel()
    }

    fun resetTimer() {
        stopTimer()
        timeInMillis = 0L
        _timeFormatted.value = "00:00:00"
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    fun addParziale(timerDisplay:String): ArrayList<String> {
        listaParziali.add("$numParziale         $timerDisplay")
        numParziale++
        return listaParziali
    }

    fun clearParziali(): ArrayList<String> {
        listaParziali.clear()
        numParziale = 1
        return listaParziali
    }

    fun getParziali(): ArrayList<String> {
        return listaParziali
    }

    private fun formatTime(timeInMillis: Long): String {
        val seconds = (timeInMillis / 1000).toInt()
        val millis = (timeInMillis % 1000) / 10
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, remainingSeconds, millis)
    }
}
