package com.example.trainingproject.RoomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entità database per le schede di allenamento
@Entity
data class Scheda(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String
)

