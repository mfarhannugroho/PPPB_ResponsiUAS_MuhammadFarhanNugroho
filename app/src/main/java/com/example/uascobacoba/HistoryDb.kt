package com.example.uascobacoba

import androidx.room.Entity
import androidx.room.PrimaryKey

// Kelas entitas untuk tabel 'history' dalam database
@Entity(tableName = "history")
data class HistoryDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,                // Primary key yang secara otomatis di-generate

    val uid: String = "",           // ID unik untuk setiap pengguna
    val stasiunAsal: String = "",   // Stasiun asal perjalanan
    val stasiunTujuan: String = "", // Stasiun tujuan perjalanan
    val kelasKereta: String = "",   // Kelas kereta yang digunakan
    val harga: String = "",         // Harga perjalanan
    val waktu: String = "",         // Waktu perjalanan
)
