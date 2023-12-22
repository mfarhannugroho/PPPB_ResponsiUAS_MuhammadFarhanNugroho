package com.example.uascobacoba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uascobacoba.databinding.ListHistoryBinding

// Kelas adapter untuk RecyclerView yang menangani tampilan item histori perjalanan
class HistoryAdapter(private val travelList: List<HistoryDb>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // Kelas ViewHolder untuk menyimpan referensi tampilan item
    inner class ViewHolder(private val binding: ListHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        // Fungsi bind untuk mengisi tampilan item dengan data dari objek HistoryDb
        fun bind(travel: HistoryDb) {
            binding.asalHistory.text = travel.stasiunAsal
            binding.tujuanHistory.text = travel.stasiunTujuan
            binding.kelasHistory.text = travel.kelasKereta
            binding.totalHistory.text = "Rp.${travel.harga}"
            binding.tanggalHistory.text = travel.waktu
        }
    }

    // Fungsi untuk membuat ViewHolder baru saat RecyclerView memerlukannya
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Fungsi untuk mendapatkan jumlah total item dalam dataset
    override fun getItemCount(): Int {
        return travelList.size
    }

    // Fungsi untuk menghubungkan data dari objek HistoryDb ke tampilan item di RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])
    }
}
