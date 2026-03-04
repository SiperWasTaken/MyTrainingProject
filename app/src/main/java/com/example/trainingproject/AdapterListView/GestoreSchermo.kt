package com.example.trainingproject.AdapterListView

// Classe per gestire le dimensioni dello schermo
class GestoreSchermo(){

    private var pollici: Float = 0.0f

    fun setDim(pol: Float){

        pollici = pol

    }

    fun getDim():Float{
        return pollici
    }

}
