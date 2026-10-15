package com.example.irw_oreo

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Sembunyikan Action Bar bawaan jika ada agar tampilan clean
        supportActionBar?.hide()

        // Inisialisasi Icon Back
        val btnBackIcon = findViewById<ImageButton>(R.id.btnBackIcon)
        btnBackIcon.setOnClickListener {
            finish() // Kembali ke MainActivity
        }
    }
}