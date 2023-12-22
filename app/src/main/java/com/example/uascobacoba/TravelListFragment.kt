package com.example.uascobacoba

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.uascobacoba.databinding.FragmentTravelListBinding

// Kelas fragment untuk menampilkan daftar perjalanan untuk pengguna
class TravelListFragment : Fragment() {

    private lateinit var binding: FragmentTravelListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTravelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()!!
        mainActivity.getTravel()

        with(binding) {
            // Menanggapi klik tombol "Logout"
            logoutUser.setOnClickListener {
                mainActivity.logout()
                requireActivity().finish()
            }

            // Menanggapi klik tombol "Favorites"
            favUser.setOnClickListener {
                findNavController().navigate(TravelListFragmentDirections.actionTravelListFragmentToFavouriteFragment())
            }

            // Menanggapi perubahan data perjalanan
            mainActivity.getTravelLiveData().observe(viewLifecycleOwner) {
                if (it != null) {
                    rv2.visibility = View.VISIBLE
                    rv2.apply {
                        adapter = TravelUserAdapter(it, onHistory = {
                            // Menanggapi klik untuk menambahkan ke riwayat
                            val hisDb = HistoryDb(
                                uid = mainActivity.getUID(),
                                stasiunAsal = it.stasiunAsal,
                                stasiunTujuan = it.stasiunTujuan,
                                kelasKereta = it.kelasKereta,
                                harga = it.harga,
                                waktu = it.waktu
                            )
                            mainActivity.insertHistory(hisDb)
                            Toast.makeText(context, "Travel Added", Toast.LENGTH_SHORT).show()
                            // Menampilkan notifikasi
                            notifyTicket("history")
                        }, onFavourite = {
                            // Menanggapi klik untuk menambahkan ke favorit
                            val travelDb = TravelDb(
                                uid = mainActivity.getUID(),
                                stasiunAsal = it.stasiunAsal,
                                stasiunTujuan = it.stasiunTujuan,
                                kelasKereta = it.kelasKereta,
                                harga = it.harga,
                                waktu = it.waktu
                            )
                            mainActivity.insertFavorite(travelDb)
                            Toast.makeText(context, "Favorite Added", Toast.LENGTH_SHORT).show()
                            // Menampilkan notifikasi
                            notifyTicket("favorite")
                        })
                        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                    }
                } else {
                    rv2.visibility = View.GONE
                }
            }
        }
    }

    // Fungsi untuk menampilkan notifikasi
    private fun notifyTicket(string: String) {
        val NOTIFICATION_ID = 1 // ID unik notifikasi

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            0
        }

        // Membuat intent untuk notifikasi
        val intent = Intent(requireContext(), NotifyReceiver::class.java).putExtra("message", "Ticket Added")
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, flag)

        // Membuat notifikasi menggunakan NotificationCompat
        val builder = NotificationCompat.Builder(requireContext(), "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Ticket Added")
            .setContentText("Anda telah menambahkan tiket, silahkan cek di halaman $string")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Menambahkan notifikasi ke NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1", "1", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "1"
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}
