package com.example.uascobacoba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uascobacoba.databinding.FragmentProfileBinding

// Kelas fragment untuk menampilkan halaman profil pengguna
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    // Fungsi onCreateView untuk menginisialisasi tata letak fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menggunakan data binding untuk meng-inflate layout fragment_profile.xml
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Fungsi onViewCreated dipanggil setelah tampilan fragment telah dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan informasi akun pengguna dari MainActivity
        val data = MainActivity.getInstance()!!.getAccountInfo()

        // Menggunakan binding untuk mengakses tampilan pada layout fragment
        with(binding) {
            // Menampilkan nama dan NIM pengguna pada tampilan TextView
            nama.text = data.first
            nim.text = data.second
        }
    }
}
