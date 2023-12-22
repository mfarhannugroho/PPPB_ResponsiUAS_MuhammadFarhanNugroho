package com.example.uascobacoba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uascobacoba.databinding.FragmentFavouriteBinding

// Kelas fragment untuk menampilkan halaman favorit
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding

    // Fungsi onCreateView untuk menginisialisasi tata letak fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menggunakan data binding untuk meng-inflate layout fragment_favourite.xml
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Fungsi onViewCreated dipanggil setelah tampilan fragment telah dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan binding untuk mengakses tampilan dan fungsi pada layout fragment
        with(binding) {
            // Mendapatkan instance MainActivity dan mengamati perubahan pada LiveData favorit
            MainActivity.getInstance()!!.getAllFavorite().observe(viewLifecycleOwner) {
                if (it != null) {
                    // Menampilkan RecyclerView jika data favorit tidak kosong
                    favPage.visibility = View.VISIBLE
                    favPage.apply {
                        // Mengatur adapter dan layout manager untuk RecyclerView
                        adapter = FavoriteAdapter(it)
                        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                    }
                } else {
                    // Menyembunyikan RecyclerView jika data favorit kosong
                    favPage.visibility = View.GONE
                }
            }
        }
    }
}
