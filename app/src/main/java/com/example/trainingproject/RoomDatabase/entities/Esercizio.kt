package com.example.trainingproject.RoomDatabase.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// Entità database per gli esercizi
@Entity(
    foreignKeys = [ForeignKey(
        entity = Scheda::class,
        parentColumns = ["id"],
        childColumns = ["schedaId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Esercizio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String?,
    val numeroSet: Int?,
    val secondiRecupero: Int?,
    val secondiEsecuzione: Int?,
    val schedaId: Int?
)
