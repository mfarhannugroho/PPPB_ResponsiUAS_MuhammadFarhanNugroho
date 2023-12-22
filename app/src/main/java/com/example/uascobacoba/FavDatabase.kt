package com.example.uascobacoba

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Anotasi @Database digunakan untuk mendefinisikan database dengan entitas TravelDb dan HistoryDb
@Database(entities = [TravelDb::class, HistoryDb::class], version = 1, exportSchema = false)
abstract class FavDatabase : RoomDatabase() {

    // Fungsi abstract untuk mendapatkan objek DAO (Data Access Object) untuk entitas Travel
    abstract fun travelDao(): TravelDao

    // Fungsi abstract untuk mendapatkan objek DAO untuk entitas History
    abstract fun historyDao(): HistoryDao

    companion object {
        // Volatile instance digunakan untuk memastikan bahwa instance ini selalu terlihat oleh semua thread
        @Volatile
        private var instance: FavDatabase? = null

        // Fungsi untuk mendapatkan instance dari database menggunakan metode Singleton
        fun getInstance(context: Context): FavDatabase? {
            synchronized(FavDatabase::class.java) {
                if (instance == null) {
                    // Membangun instance database menggunakan Room
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavDatabase::class.java, "travel.db"
                    ).build()
                }
                return instance
            }
        }
    }
}
