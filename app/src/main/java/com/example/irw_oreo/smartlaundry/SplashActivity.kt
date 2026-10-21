package com.example.irw_oreo.smartlaundry

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.irw_oreo.BaseActivity
import com.example.irw_oreo.PreferencesManager
import com.example.irw_oreo.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val prefManager = PreferencesManager(this)

            if (prefManager.isLoggedIn()) {
                // Jika BELUM logout, tetap di Dashboard (BaseActivity)
                startActivity(Intent(this, BaseActivity::class.java))
            } else {
                // Jika SUDAH logout, masuk ke halaman Login / Regis
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000) // Delay 2 detik
    }
}