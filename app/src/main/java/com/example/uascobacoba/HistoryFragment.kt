package com.example.uascobacoba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uascobacoba.databinding.FragmentHistoryBinding

// Kelas fragment untuk menampilkan halaman histori perjalanan
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    // Fungsi onCreateView untuk menginisialisasi tata letak fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menggunakan data binding untuk meng-inflate layout fragment_history.xml
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Fungsi onViewCreated dipanggil setelah tampilan fragment telah dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan binding untuk mengakses tampilan dan fungsi pada layout fragment
        with(binding) {
            // Mendapatkan instance MainActivity dan mengamati perubahan pada LiveData histori perjalanan
            MainActivity.getInstance()!!.getAllHistory().observe(viewLifecycleOwner) {
                if (it != null) {
                    // Menampilkan RecyclerView jika data histori perjalanan tidak kosong
                    historyPage.visibility = View.VISIBLE
                    historyPage.apply {
                        // Mengatur adapter dan layout manager untuk RecyclerView
                        adapter = HistoryAdapter(it)
                        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                    }
                } else {
                    // Menyembunyikan RecyclerView jika data histori perjalanan kosong
                    historyPage.visibility = View.GONE
                }
            }
        }
    }
}
