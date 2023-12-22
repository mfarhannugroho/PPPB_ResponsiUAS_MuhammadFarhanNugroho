package com.example.uascobacoba

// Data class Akun untuk merepresentasikan informasi akun pengguna
data class Akun(
    var id: String = "",          // ID unik untuk setiap akun
    val nama: String = "",        // Nama lengkap pengguna
    val nim: String = "",         // Nomor Induk Mahasiswa
    val username: String = "",    // Nama pengguna (username) untuk login
    val email: String = "",       // Alamat email pengguna
    val password : String = "",   // Kata sandi pengguna
    val isAdmin: Boolean = false, // Flag untuk menandai apakah pengguna adalah admin atau tidak
)
