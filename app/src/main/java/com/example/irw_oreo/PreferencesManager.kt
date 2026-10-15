package com.example.irw_oreo

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)

    fun setLogin(isLogin: Boolean) {
        pref.edit().putBoolean("IS_LOGIN", isLogin).apply()
    }

    fun isLogin(): Boolean {
        return pref.getBoolean("IS_LOGIN", false)
    }

    // Menghapus seluruh session/status login
    fun clearSession() {
        pref.edit().clear().apply()
    }
}