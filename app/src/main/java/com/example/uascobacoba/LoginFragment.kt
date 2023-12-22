package com.example.uascobacoba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uascobacoba.databinding.FragmentLoginBinding

// Kelas fragment untuk menampilkan halaman login
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    // Fungsi onCreateView untuk menginisialisasi tata letak fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menggunakan data binding untuk meng-inflate layout fragment_login.xml
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Fungsi onViewCreated dipanggil setelah tampilan fragment telah dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan binding untuk mengakses tampilan dan fungsi pada layout fragment
        with(binding) {
            // Menanggapi aksi klik pada tombol login
            btLoginLogin.setOnClickListener {
                // Mendapatkan instance MainActivity dan memanggil fungsi getAkun untuk melakukan login
                MainActivity.getInstance()!!.getAkun(username = etLoginUsername.text.toString(), password = etLoginPass.text.toString())
            }
        }
    }
}
