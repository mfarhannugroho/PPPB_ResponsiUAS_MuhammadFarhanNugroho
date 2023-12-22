package com.example.uascobacoba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uascobacoba.databinding.ListFavouriteBinding

// Kelas adapter untuk RecyclerView yang menangani tampilan item favorit
class FavoriteAdapter(private val travelList: List<Travel>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    // Kelas ViewHolder untuk menyimpan referensi tampilan item
    inner class ViewHolder(private val binding: ListFavouriteBinding) : RecyclerView.ViewHolder(binding.root) {

        // Fungsi bind untuk mengisi tampilan item dengan data dari objek Travel
        fun bind(travel: Travel) {
            binding.asalFav.text = travel.stasiunAsal
            binding.tujuanFav.text = travel.stasiunTujuan
            binding.kelasFav.text = travel.kelasKereta
            binding.totalFav.text = "Rp.${travel.harga}"
            binding.tanggalFav.text = travel.waktu
        }
    }

    // Fungsi untuk membuat ViewHolder baru saat RecyclerView memerlukannya
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Fungsi untuk mendapatkan jumlah total item dalam dataset
    override fun getItemCount(): Int {
        return travelList.size
    }

    // Fungsi untuk menghubungkan data dari objek Travel ke tampilan item di RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])
    }
}
