package com.example.irw_oreo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Hubungkan dengan Toolbar dari XML
        val toolbar: Toolbar = findViewById(R.id.toolbarDetail)
        setSupportActionBar(toolbar)

        // Tampilkan Judul dan Tombol Back (←)
        supportActionBar?.title = "Detail Oreo"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Fungsi klik tombol back di Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}