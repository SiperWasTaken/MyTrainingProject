package com.example.trainingproject.RoomDatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trainingproject.RoomDatabase.DAO.SchedaEsercizioDao
import com.example.trainingproject.RoomDatabase.entities.Esercizio
import com.example.trainingproject.RoomDatabase.entities.Scheda

@Database(entities = [Scheda::class, Esercizio::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun schedaEsercizioDao(): SchedaEsercizioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
