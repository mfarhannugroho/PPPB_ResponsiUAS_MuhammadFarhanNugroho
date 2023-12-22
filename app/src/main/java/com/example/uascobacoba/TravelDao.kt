package com.example.uascobacoba

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Antarmuka Dao (Data Access Object) untuk entitas Travel
@Dao
interface TravelDao {

    // Fungsi insertTravel untuk menambahkan atau mengganti data perjalanan ke dalam database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTravel(travelDb: TravelDb)

    // Fungsi getTravelById untuk mendapatkan data perjalanan berdasarkan UID
    @Query("SELECT * FROM travel where uid = :uid")
    fun getTravelById(uid: String): LiveData<List<Travel>>
}