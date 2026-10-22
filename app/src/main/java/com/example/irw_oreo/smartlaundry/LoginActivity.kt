package com.example.irw_oreo.smartlaundry

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.irw_oreo.BaseActivity
import com.example.irw_oreo.PreferencesManager
import com.example.irw_oreo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferencesManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Ambil data registrasi dari SharedPreferences
            val regData = prefManager.getRegisterData()
            val savedUsername = regData["username"]
            val savedPassword = regData["password"]

            // Cek apakah user sudah pernah registrasi
            val isRegistered = !savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()

            if (!isRegistered) {
                // Jika belum registrasi, tolak login dan minta registrasi dulu
                Toast.makeText(this, "Anda belum terdaftar! Silakan registrasi terlebih dahulu.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Jika SUDAH terdaftar, baru jalankan 2 Rule Login:
            // Rule 1: Username = Password[cite: 4]
            val isRule1Valid = username.isNotEmpty() && username == password

            // Rule 2: Username & Password sesuai dengan data registrasi di SharedPreferences[cite: 4]
            val isRule2Valid = username == savedUsername && password == savedPassword

            if (isRule1Valid || isRule2Valid) {
                prefManager.setLoggedIn(true)

                val intent = Intent(this, BaseActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login gagal: Username atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigasi ke Halaman Registrasi
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Otomatis isi kolom Username & Password dari data yang tersimpan setelah registrasi
    override fun onResume() {
        super.onResume()
        val regData = prefManager.getRegisterData()
        val savedUsername = regData["username"]
        val savedPassword = regData["password"]

        if (!savedUsername.isNullOrEmpty()) {
            binding.etUsername.setText(savedUsername)
        }
        if (!savedPassword.isNullOrEmpty()) {
            binding.etPassword.setText(savedPassword)
        }
    }
}