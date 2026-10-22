package com.example.irw_oreo.smartlaundry

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.irw_oreo.PreferencesManager
import com.example.irw_oreo.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    private lateinit var prefManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferencesManager(this)

        // Ambil data yang tersimpan dari SharedPreferences
        val data = prefManager.getRegisterData()

        // Tampilkan seluruh isian di layar verifikasi
        binding.tvDisplayData.text = """
            Nama            : ${data["nama"]}
            Email           : ${data["email"]}
            Tanggal Lahir   : ${data["tglLahir"]}
            Jenis Kelamin   : ${data["gender"]}
            Username        : ${data["username"]}
            Password        : ${data["password"]}
            Confirm Password: ${data["confirmPass"]}
        """.trimIndent()

        // Tombol "Kembali" untuk memperbaiki isian di SharedPreferences
        binding.btnKembali.setOnClickListener {
            finish() // Kembali ke RegisterActivity
        }

        // Tombol "Submit" untuk melakukan validasi
        binding.btnSubmit.setOnClickListener {
            val nama = data["nama"] ?: ""
            val email = data["email"] ?: ""
            val tglLahir = data["tglLahir"] ?: ""
            val gender = data["gender"] ?: ""
            val username = data["username"] ?: ""
            val pass = data["password"] ?: ""
            val confirmPass = data["confirmPass"] ?: ""

            // 1. Cek semua inputan tidak boleh kosong
            if (nama.isEmpty() || email.isEmpty() || tglLahir.isEmpty() ||
                gender.isEmpty() || username.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()
            ) {
                Toast.makeText(this, "Validasi Gagal: Ada isian form yang masih kosong!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // 2. Cek Password dan Confirm Password harus sama
            if (pass != confirmPass) {
                Toast.makeText(this, "Validasi Gagal: Password dan Confirm Password tidak cocok!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // 3. Jika berhasil melewati validasi -> Tampilkan "Registrasi Berhasil"
            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_LONG).show()

            // Arahkan ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }
}