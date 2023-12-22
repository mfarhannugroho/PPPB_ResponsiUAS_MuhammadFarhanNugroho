package com.example.uascobacoba

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.uascobacoba.databinding.ActivityAddTravelBinding

class AddTravelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTravelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi binding untuk layout activity_add_travel.xml
        binding = ActivityAddTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Variabel untuk menyimpan waktu
        var waktu = ""

        // Variabel untuk menandai apakah ini operasi update atau tidak
        var update = false;

        with(binding) {
            // Mendapatkan instance MainActivity
            val mainActivity = MainActivity.getInstance()!!

            // Mendapatkan data dari intent
            val data = intent.getBundleExtra("data")
            Log.d("data", data?.getString("id").toString())

            // Mengisi field dengan data jika ada
            if (data != null) {
                etAsal.setText(data.getString("asal"))
                etTujuan.setText(data.getString("tujuan"))
                etKelas.setText(data.getString("kelas"))
                etHarga.setText(data.getString("harga"))
                update = true
            }

            // Menambahkan listener untuk memperbarui variabel waktu saat tanggal diubah
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateTrip.setOnDateChangedListener { datePicker, i, i2, i3 ->
                    waktu = "$i-$i2-$i3"
                }
            }

            // Menutup activity jika tombol closeAdd ditekan
            closeAdd.setOnClickListener {
                finish()
            }

            // Menyimpan atau memperbarui data ke dalam MainActivity dan menutup activity
            doneAdd.setOnClickListener {
                if(update) {
                    val travel = Travel(
                        id = data?.getString("id").toString(),
                        stasiunAsal = etAsal.text.toString(),
                        stasiunTujuan = etTujuan.text.toString(),
                        kelasKereta = etKelas.text.toString(),
                        harga = etHarga.text.toString(),
                        waktu = waktu
                    )
                    mainActivity.updateTravel(travel)
                } else {
                    mainActivity.addTravel(
                        stasiunAsal = etAsal.text.toString(),
                        stasiunTujuan = etTujuan.text.toString(),
                        kelasKereta = etKelas.text.toString(),
                        harga = etHarga.text.toString(),
                        waktu = waktu
                    )
                }

                finish()
            }

        }
    }
}
