package com.example.uascobacoba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uascobacoba.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi binding untuk layout activity_admin.xml
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan instance MainActivity
        val mainActivity = MainActivity.getInstance()!!

        // Memanggil fungsi getTravel() untuk mendapatkan data perjalanan
        mainActivity.getTravel()

        // Mendapatkan LiveData dari data perjalanan
        val data = mainActivity.getTravelLiveData()

        with(binding){
            // Mengamati perubahan pada LiveData dan menanggapi perubahan tersebut
            data.observe(this@AdminActivity){
                if(it.isNotEmpty()) {
                    // Menampilkan RecyclerView jika data tidak kosong
                    rv.visibility = android.view.View.VISIBLE
                    rv.apply {
                        // Mengatur adapter untuk RecyclerView dan menangani aksi edit dan delete
                        adapter = TravelAdminAdapter(it, onEdit = {
                            val bundle = Bundle()
                            bundle.putString("id", it.id)
                            bundle.putString("asal", it.stasiunAsal)
                            bundle.putString("tujuan", it.stasiunTujuan)
                            bundle.putString("kelas", it.kelasKereta)
                            bundle.putString("harga", it.harga)
                            bundle.putString("waktu", it.waktu)
                            val intent = Intent(this@AdminActivity, AddTravelActivity::class.java)
                            intent.putExtra("data", bundle)
                            startActivity(intent)
                        }, onDelete = {
                            // Menghapus data perjalanan
                            mainActivity.deleteTravel(it)
                            Toast.makeText(this@AdminActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                        })
                        // Mengatur layout manager untuk RecyclerView
                        layoutManager = LinearLayoutManager(this@AdminActivity)
                    }
                } else {
                    // Menyembunyikan RecyclerView jika data kosong
                    rv.visibility = android.view.View.GONE
                }
            }

            // Menambahkan listener untuk tombol btnAdd untuk membuka AddTravelActivity
            btnAdd.setOnClickListener {
                val intent = Intent(this@AdminActivity,AddTravelActivity::class.java)
                startActivity(intent)
            }

            // Menambahkan listener untuk tombol logoutAdmin untuk logout dari aplikasi
            logoutAdmin.setOnClickListener {
                mainActivity.logout()
                finish()
            }
        }
    }
}
