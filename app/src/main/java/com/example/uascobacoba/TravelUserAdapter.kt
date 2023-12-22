package com.example.uascobacoba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uascobacoba.databinding.ListUserBinding

// Kelas adapter untuk RecyclerView di tampilan pengguna
class TravelUserAdapter(
    private val travelList: List<Travel>,
    private val onFavourite: (Travel) -> Unit = {},
    private val onHistory: (Travel) -> Unit = {}
) : RecyclerView.Adapter<TravelUserAdapter.ViewHolder>() {

    // Kelas ViewHolder untuk menghubungkan tampilan item dalam RecyclerView
    inner class ViewHolder(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        // Fungsi untuk mengikat data perjalanan ke tampilan item
        fun bind(travel: Travel) {
            binding.asalUser.text = travel.stasiunAsal
            binding.tujuanUser.text = travel.stasiunTujuan
            binding.kelasUser.text = travel.kelasKereta
            binding.totalUser.text = "Rp.${travel.harga}"
            binding.tanggalUser.text = travel.waktu

            // Menanggapi klik tombol "Favourite"
            binding.favouriteButton.setOnClickListener {
                onFavourite(travel)
            }

            // Menanggapi klik tombol "Buy"
            binding.buyButton.setOnClickListener {
                onHistory(travel)
            }
        }
    }

    // Metode untuk membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Metode untuk mendapatkan jumlah item dalam RecyclerView
    override fun getItemCount(): Int {
        return travelList.size
    }

    // Metode untuk mengikat data perjalanan ke ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])
    }
}
