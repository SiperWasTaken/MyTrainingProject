package com.example.trainingproject.RoomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Scheda(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String
)

