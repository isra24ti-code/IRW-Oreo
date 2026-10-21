package com.example.irw_oreo.smartlaundry

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.irw_oreo.R

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