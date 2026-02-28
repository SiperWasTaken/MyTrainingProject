package com.example.trainingproject.RoomDatabase.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainingproject.RoomDatabase.entities.Esercizio
import com.example.trainingproject.RoomDatabase.entities.Scheda

@Dao
interface SchedaEsercizioDao{

    // Operazioni sulla tabella Scheda
    @Insert
    suspend fun insertScheda(scheda: Scheda): Long

    @Query("SELECT * FROM Scheda")
    suspend fun getAllSchede(): List<Scheda>

    @Query("DELETE FROM Scheda WHERE id = :schedaId")
    suspend fun deleteSchedaById(schedaId: Int)

    @Query("UPDATE scheda SET nome = :nuovoNome WHERE id = :idScheda")
    suspend fun aggiornaNomeScheda(idScheda: Int, nuovoNome: String)


    // Operazioni sulla tabella Esercizio
    @Insert
    suspend fun insertEsercizio(esercizio: Esercizio): Long

    @Query("SELECT * FROM Esercizio WHERE schedaId = :schedaId")
    suspend fun getEserciziByScheda(schedaId: Int): List<Esercizio>

    @Query("DELETE FROM Esercizio WHERE id = :esercizioId")
    suspend fun deleteEsercizioById(esercizioId: Int)

    @Query("UPDATE Esercizio SET nome = :nuovoNome WHERE id = :idEsercizio")
    suspend fun aggiornaNomeEsercizio(idEsercizio: Int, nuovoNome: String)
}
