package com.example.irw_oreo.smartlaundry

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.irw_oreo.PreferencesManager
import com.example.irw_oreo.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prefManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PreferencesManager(this)

        // DatePicker untuk Tanggal Lahir
        binding.etRegTglLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etRegTglLahir.setText(formattedDate)
            }, year, month, day)

            dpd.show()
        }

        // Tombol "Selanjutnya" -> Simpan data ke SharedPreferences & Pindah ke Verifikasi
        binding.btnSelanjutnya.setOnClickListener {
            val nama = binding.etRegNama.text.toString().trim()
            val email = binding.etRegEmail.text.toString().trim()
            val tglLahir = binding.etRegTglLahir.text.toString().trim()

            val selectedGenderId = binding.rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else ""

            val username = binding.etRegUsername.text.toString().trim()
            val pass = binding.etRegPassword.text.toString().trim()
            val confirmPass = binding.etRegConfirmPass.text.toString().trim()

            // Simpan isian ke SharedPreferences
            prefManager.saveRegisterData(nama, email, tglLahir, gender, username, pass, confirmPass)

            // Lanjut ke Halaman Verifikasi
            startActivity(Intent(this, VerificationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Muat isian tersimpan dari SP jika user menekan tombol "Kembali" untuk memperbaiki data
        val data = prefManager.getRegisterData()
        if (data["nama"]?.isNotEmpty() == true) binding.etRegNama.setText(data["nama"])
        if (data["email"]?.isNotEmpty() == true) binding.etRegEmail.setText(data["email"])
        if (data["tglLahir"]?.isNotEmpty() == true) binding.etRegTglLahir.setText(data["tglLahir"])
        if (data["username"]?.isNotEmpty() == true) binding.etRegUsername.setText(data["username"])
        if (data["password"]?.isNotEmpty() == true) binding.etRegPassword.setText(data["password"])
        if (data["confirmPass"]?.isNotEmpty() == true) binding.etRegConfirmPass.setText(data["confirmPass"])

        if (data["gender"] == "Laki-laki") binding.rbLaki.isChecked = true
        else if (data["gender"] == "Perempuan") binding.rbPerempuan.isChecked = true
    }
}
