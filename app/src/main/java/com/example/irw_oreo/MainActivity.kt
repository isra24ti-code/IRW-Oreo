package com.example.irw_oreo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Hubungkan ID dari XML
        val btnDetail = findViewById<Button>(R.id.btnDetail)
        val btnWeb = findViewById<Button>(R.id.btnWeb)
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val pref = PreferencesManager(this)

        // 2. Klik Tombol Detail
        btnDetail?.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        // 3. Klik Tombol Web
        btnWeb?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }

        // 4. Klik Tombol Profil Developer
        btnProfile?.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // 5. Klik Tombol Logout
        btnLogout?.setOnClickListener {
            // A. Reset Status Login di SharedPreferences
            pref.clearSession()

            // B. Tampilkan Toast
            Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()

            // C. Pindah ke LoginActivity & Hapus Backstack
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}