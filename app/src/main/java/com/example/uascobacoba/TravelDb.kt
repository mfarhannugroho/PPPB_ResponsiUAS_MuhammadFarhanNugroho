package com.example.uascobacoba

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel")
data class TravelDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val uid: String = "",
    val stasiunAsal: String = "",
    val stasiunTujuan: String = "",
    val kelasKereta: String = "",
    val harga: String = "",
    val waktu: String = "",
)