package com.example.uascobacoba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.example.uascobacoba.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Kelas utama untuk aplikasi
class MainActivity : AppCompatActivity() {

    // Variabel untuk data binding
    private lateinit var binding: ActivityMainBinding

    // Variabel untuk ViewPager dan TabLayout
    private lateinit var adapter: ViewPagerAdapter

    // Variabel untuk menyimpan preferensi pengguna
    private lateinit var preferences: Preferences

    // Variabel untuk Firebase Firestore
    private lateinit var firestore: FirebaseFirestore
    private lateinit var accountCollection: CollectionReference
    private lateinit var travelCollection: CollectionReference

    // Variabel untuk Room Database dan ExecutorService
    private lateinit var roomdb: FavDatabase
    private lateinit var favDao: TravelDao
    private lateinit var hisDao: HistoryDao
    private lateinit var executorService: ExecutorService

    // Variabel untuk LiveData dan instance MainActivity
    private val travelData: MutableLiveData<List<Travel>> by lazy {
        MutableLiveData<List<Travel>>()
    }

    companion object {
        private var instance: MainActivity? = null

        fun getInstance(): MainActivity? {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi instance MainActivity
        instance = this

        // Inisialisasi Preferences
        preferences = Preferences.getInstance(this)

        // Inisialisasi Firebase Firestore
        firestore = FirebaseFirestore.getInstance()
        accountCollection = firestore.collection("account")
        travelCollection = firestore.collection("travel")

        // Inisialisasi Room Database dan ExecutorService
        roomdb = FavDatabase.getInstance(this)!!
        favDao = roomdb.travelDao()
        hisDao = roomdb.historyDao()
        executorService = Executors.newSingleThreadExecutor()

        with(binding) {
            // Menambahkan tab login dan register ke TabLayout
            tabLayout.addTab(tabLayout.newTab().setText("Login"))
            tabLayout.addTab(tabLayout.newTab().setText("Register"))

            // Mendapatkan FragmentManager dan mengatur adapter untuk ViewPager
            val fragmentManager: FragmentManager = supportFragmentManager
            adapter = ViewPagerAdapter(fragmentManager, lifecycle)
            viewPager.adapter = adapter

            // Menanggapi perubahan pada TabLayout dan ViewPager
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            // Menanggapi perubahan pada ViewPager dan mengubah TabLayout
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })

            // Jika pengguna sudah login, langsung arahkan ke halaman sesuai dengan peran pengguna
            if(preferences.isLoggedIn()) {
                if(preferences.isAdmin()) {
                    val intent = Intent(this@MainActivity, AdminActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@MainActivity, UserActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    // Fungsi untuk mendapatkan informasi akun pengguna
    fun getAccountInfo(): Pair<String, String> {
        return Pair(preferences.getFullName(), preferences.getNim())
    }

    // Fungsi untuk menyimpan data histori perjalanan ke dalam database Room
    fun insertHistory(historyDb: HistoryDb) {
        executorService.execute {
            hisDao.insertHistory(historyDb)
        }
    }

    // Fungsi untuk mendapatkan LiveData histori perjalanan
    fun getAllHistory(): LiveData<List<HistoryDb>> {
        return hisDao.getHistoryById(preferences.getUID())
    }

    // Fungsi untuk menyimpan data perjalanan favorit ke dalam database Room
    fun insertFavorite(travel: TravelDb) {
        executorService.execute {
            favDao.insertTravel(travel)
        }
    }

    // Fungsi untuk mendapatkan LiveData perjalanan favorit
    fun getAllFavorite(): LiveData<List<Travel>> {
        return favDao.getTravelById(preferences.getUID())
    }

    // Fungsi untuk mendapatkan LiveData perjalanan dari Firebase Firestore
    fun getTravelLiveData(): MutableLiveData<List<Travel>> {
        return travelData
    }

    // Fungsi untuk mendapatkan data perjalanan dari Firebase Firestore
    fun getTravel() {
        travelCollection.addSnapshotListener { value, error ->
            if(error != null) {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            val travel = value?.toObjects(Travel::class.java)
            if(travel != null) {
                travelData.postValue(travel)
            }
        }
    }

    // Fungsi untuk menghapus data perjalanan dari Firebase Firestore
    fun deleteTravel(travel: Travel) {
        travelCollection.document(travel.id).delete()
            .addOnSuccessListener {
                Log.d("TravelActivity", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("TravelActivity", "Error deleting document", e)
            }
    }

    // Fungsi untuk mengupdate data perjalanan di Firebase Firestore
    fun updateTravel(travel: Travel) {
        travelCollection.document(travel.id).set(travel)
            .addOnSuccessListener {
                Log.d("TravelActivity", "DocumentSnapshot successfully updated!")
                Toast.makeText(this, "Data Berhasilal Diupdate", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("TravelActivity", "Error updating document", e)
            }
    }

    // Fungsi untuk menambahkan data perjalanan ke Firebase Firestore
    fun addTravel(stasiunAsal: String, stasiunTujuan: String, kelasKereta: String, harga: String, waktu: String) {
        val travel = Travel(
            stasiunAsal = stasiunAsal,
            stasiunTujuan = stasiunTujuan,
            kelasKereta = kelasKereta,
            harga = harga,
            waktu = waktu
        )
        travelCollection.add(travel)
            .addOnSuccessListener { documentReference ->
                travel.id = documentReference.id
                documentReference.set(travel)
                    .addOnSuccessListener {
                        Log.d("TravelActivity", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TravelActivity", "Error adding document", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.w("TravelActivity", "Error adding document", e)
            }
    }

    // Fungsi untuk mendapatkan UID pengguna
    fun getUID(): String {
        return preferences.getUID()
    }

    // Fungsi untuk melakukan login
    private fun login(username: String, password: String) {
        if(username== preferences.getUsername() && password == preferences.getPassword()) {
            preferences.setLoggedIn(true, preferences.isAdmin())
            if(preferences.isAdmin()) {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
        } else {
            preferences.clear()
            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk mendapatkan informasi akun dari Firebase Firestore
    fun getAkun(username: String, password: String) {
        accountCollection.whereEqualTo("username", username).limit(1).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val data = documents.documents[0].data
                    if (data != null) {
                        preferences.saveUID(data["id"].toString())
                        preferences.saveFullName(data["nama"].toString())
                        preferences.saveUsername(data["username"].toString())
                        preferences.saveNim(data["nim"].toString())
                        preferences.saveEmail(data["email"].toString())
                        preferences.savePassword(data["password"].toString())
                        if(data["admin"] == true) {
                            preferences.setAdmin()
                        }
                        login(username, password)
                    }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }

    }

    // Fungsi untuk mendaftarkan akun baru di Firebase Firestore
    fun registerAccount(nama: String, nim: String, username: String, email: String, password: String) {
        val akun = Akun(
            nama = nama,
            nim = nim,
            username = username,
            email = email,
            password = password
        )
        accountCollection.add(akun)
            .addOnSuccessListener { documentReference ->
                akun.id = documentReference.id
                documentReference.set(akun)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Berhasil Register", Toast.LENGTH_SHORT).show()
                        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                    }
                    .addOnFailureListener { e ->
                        println("Error adding document: $e")
                    }
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }

    fun logout() {
        preferences.clear()
    }
}