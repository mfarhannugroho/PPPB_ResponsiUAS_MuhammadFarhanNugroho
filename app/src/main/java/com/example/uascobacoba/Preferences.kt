package com.example.uascobacoba

import android.content.Context
import android.content.SharedPreferences

// Kelas Preferences untuk mengelola penyimpanan preferensi aplikasi
class Preferences private constructor(context: Context) {
    private val preferences: SharedPreferences

    companion object {
        private const val PREF_FILE_NAME = "session"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val IS_ADMIN = "is_admin"
        private const val UID = "uid"
        private const val FULL_NAME = "full_name"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val EMAIL = "email"
        private const val NIM = "nim"

        // Instance Preferences yang bersifat singleton
        @Volatile
        private var instance: Preferences? = null

        // Fungsi getInstance untuk mendapatkan instance Preferences
        fun getInstance(context: Context): Preferences {
            return instance ?: synchronized(this) {
                instance ?: Preferences(context.applicationContext).also { instance = it }
            }
        }
    }

    init {
        // Inisialisasi Shared Preferences
        preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    // Fungsi untuk menyimpan status login dan status admin ke dalam Shared Preferences
    fun setLoggedIn(isLoggedIn: Boolean, isAdmin: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putBoolean(IS_ADMIN, isAdmin)
        editor.apply()
    }

    // Fungsi untuk mendapatkan status login dari Shared Preferences
    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(IS_LOGGED_IN, false)
    }

    // Fungsi untuk mendapatkan status admin dari Shared Preferences
    fun isAdmin(): Boolean {
        return preferences.getBoolean(IS_ADMIN, false)
    }

    // Fungsi untuk mengatur status admin menjadi true
    fun setAdmin() {
        val editor = preferences.edit()
        editor.putBoolean(IS_ADMIN, true)
        editor.apply()
    }

    // Fungsi-fungsi berikut untuk menyimpan dan mendapatkan data pengguna dari Shared Preferences
    fun saveUID(uid: String) {
        val editor = preferences.edit()
        editor.putString(UID, uid)
        editor.apply()
    }

    fun getUID(): String {
        return preferences.getString(UID, "")!!
    }

    fun saveFullName(fullName: String) {
        val editor = preferences.edit()
        editor.putString(FULL_NAME, fullName)
        editor.apply()
    }

    fun getFullName(): String {
        return preferences.getString(FULL_NAME, "")!!
    }

    fun saveUsername(username: String) {
        val editor = preferences.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String {
        return preferences.getString(USERNAME, "")!!
    }

    fun savePassword(password: String) {
        val editor = preferences.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun getPassword(): String {
        return preferences.getString(PASSWORD, "")!!
    }

    fun saveNim(nim: String) {
        val editor = preferences.edit()
        editor.putString(NIM, nim)
        editor.apply()
    }

    fun getNim(): String {
        return preferences.getString(NIM, "")!!
    }

    fun saveEmail(email: String) {
        val editor = preferences.edit()
        editor.putString(EMAIL, email)
        editor.apply()
    }

    fun getEmail(): String {
        return preferences.getString(EMAIL, "")!!
    }

    // Fungsi untuk menghapus semua data dari Shared Preferences
    fun clear() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}
