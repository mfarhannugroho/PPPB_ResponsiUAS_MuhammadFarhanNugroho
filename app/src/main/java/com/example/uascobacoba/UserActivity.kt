package com.example.uascobacoba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uascobacoba.databinding.ActivityUserBinding

// Kelas aktivitas untuk tampilan pengguna
class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val mainActivity = MainActivity.getInstance()!!

        // Mendapatkan data perjalanan
        mainActivity.getTravel()

        // Menetapkan layout menggunakan binding
        setContentView(binding.root)

        with(binding) {
            // Mendapatkan NavController dan menetapkan dengan BottomNavigationView
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavigationView.setupWithNavController(navController)
        }
    }
}
