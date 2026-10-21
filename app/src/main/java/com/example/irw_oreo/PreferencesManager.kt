package com.example.irw_oreo

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("OreoSession", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPref.edit().putBoolean("IS_LOGGED_IN", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("IS_LOGGED_IN", false)
    }

    // Ubah status login jadi false
    fun setLogout() {
        sharedPref.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }

    // Hapus seluruh session/data login (mencegah error Unresolved reference 'clearSession')
    fun clearSession() {
        sharedPref.edit().clear().apply()
    }
}