package com.example.irw_oreo.smartlaundry

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.irw_oreo.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengaktifkan Toolbar dan Tombol Back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Smart Laundry"

        // Aksi tombol order
        binding.btnOrderNow.setOnClickListener {
            Toast.makeText(this, "Fitur pemesanan berhasil dipilih!", Toast.LENGTH_SHORT).show()
        }
    }

    // Aksi saat tombol Back di Toolbar diklik
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}