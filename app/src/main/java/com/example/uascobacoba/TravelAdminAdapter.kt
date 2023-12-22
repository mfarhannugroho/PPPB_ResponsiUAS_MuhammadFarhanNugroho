package com.example.uascobacoba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uascobacoba.databinding.ListDashboardBinding

// Adapter untuk RecyclerView yang menampilkan data perjalanan dalam mode administrasi
class TravelAdminAdapter(
    private val travelList: List<Travel>,
    private val onEdit: (Travel) -> Unit = {},
    private val onDelete: (Travel) -> Unit = {}
) : RecyclerView.Adapter<TravelAdminAdapter.ViewHolder>() {

    // Kelas ViewHolder untuk menyimpan referensi tampilan dalam setiap item RecyclerView
    inner class ViewHolder(private val binding: ListDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Fungsi bind untuk mengisi data perjalanan ke dalam tampilan item
        fun bind(travel: Travel) {
            binding.asalText.text = travel.stasiunAsal
            binding.tujuanText.text = travel.stasiunTujuan
            binding.kelas.text = travel.kelasKereta
            binding.total.text = "Rp.${travel.harga}"
            binding.tanggal.text = travel.waktu

            // Menanggapi klik tombol "Update"
            binding.updateButton.setOnClickListener {
                onEdit(travel)
            }

            // Menanggapi klik tombol "Delete"
            binding.deleteButton.setOnClickListener {
                onDelete(travel)
            }
        }
    }

    // Fungsi onCreateViewHolder untuk membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Fungsi getItemCount untuk mendapatkan jumlah item dalam dataset
    override fun getItemCount(): Int {
        return travelList.size
    }

    // Fungsi onBindViewHolder untuk menghubungkan data ke tampilan setiap item dalam RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelList[position])
    }
}
