package com.example.uascobacoba

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object (DAO) untuk entitas HistoryDb
@Dao
interface HistoryDao {

    // Fungsi untuk memasukkan data histori perjalanan ke dalam database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHistory(historyDb: HistoryDb)

    // Fungsi untuk mendapatkan histori perjalanan berdasarkan ID pengguna
    @Query("SELECT * FROM history where uid = :uid")
    fun getHistoryById(uid: String): LiveData<List<HistoryDb>>
}
