package com.example.irw_oreo

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("SmartLaundryPref", Context.MODE_PRIVATE)

    // Simpan semua inputan registrasi (Soal 1)
    fun saveRegisterData(
        nama: String, email: String, tglLahir: String,
        gender: String, username: String, pass: String, confirmPass: String
    ) {
        pref.edit().apply {
            putString("KEY_NAMA", nama)
            putString("KEY_EMAIL", email)
            putString("KEY_TGL_LAHIR", tglLahir)
            putString("KEY_GENDER", gender)
            putString("KEY_USERNAME", username)
            putString("KEY_PASSWORD", pass)
            putString("KEY_CONFIRM_PASS", confirmPass)
            apply()
        }
    }

    // Ambil data registrasi untuk verifikasi & login (Soal 1 & 2)
    fun getRegisterData(): Map<String, String> {
        return mapOf(
            "nama" to (pref.getString("KEY_NAMA", "") ?: ""),
            "email" to (pref.getString("KEY_EMAIL", "") ?: ""),
            "tglLahir" to (pref.getString("KEY_TGL_LAHIR", "") ?: ""),
            "gender" to (pref.getString("KEY_GENDER", "") ?: ""),
            "username" to (pref.getString("KEY_USERNAME", "") ?: ""),
            "password" to (pref.getString("KEY_PASSWORD", "") ?: ""),
            "confirmPass" to (pref.getString("KEY_CONFIRM_PASS", "") ?: "")
        )
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        pref.edit().putBoolean("IS_LOGGED_IN", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean = pref.getBoolean("IS_LOGGED_IN", false)

    fun clearSession() {
        pref.edit().clear().apply()
    }
}