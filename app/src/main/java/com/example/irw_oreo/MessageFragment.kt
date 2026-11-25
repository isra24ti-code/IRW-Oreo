package com.example.irw_oreo

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.irw_oreo.databinding.FragmentMessageBinding
import com.example.irw_oreo.smartlaundry.tutorial.TutorialMessageActivity
import kotlinx.coroutines.launch

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarMessage)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Status Pesanan"

        // Setup Option Menu pada Toolbar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.message_toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_tutorial -> {
                        openTutorial()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Banner click listener
        binding.cardTutorialBanner.setOnClickListener { openTutorial() }
        binding.btnOpenTutorial.setOnClickListener { openTutorial() }

        // Tombol Tambah Pesanan (CREATE)
        binding.btnTambahPesanan.setOnClickListener {
            tampilkanDialogPesanan(null)
        }

        // Muat daftar pesanan dari Supabase (READ)
        loadDataPesanan()
    }

    private fun loadDataPesanan() {
        binding.tvInfoPesanan.visibility = View.VISIBLE
        binding.tvInfoPesanan.text = "Memuat data pesanan..."

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val daftarPesanan = SupabaseClient.apiService.getAllPesanan()
                binding.containerPesanan.removeAllViews()

                if (daftarPesanan.isEmpty()) {
                    binding.tvInfoPesanan.visibility = View.VISIBLE
                    binding.tvInfoPesanan.text = "Belum ada pesanan. Klik '+ Pesanan' untuk menambah."
                } else {
                    binding.tvInfoPesanan.visibility = View.GONE
                    for (item in daftarPesanan) {
                        binding.containerPesanan.addView(buatCardPesanan(item))
                    }
                }
            } catch (e: Exception) {
                binding.tvInfoPesanan.visibility = View.VISIBLE
                binding.tvInfoPesanan.text = "Gagal memuat pesanan: ${e.message}"
            }
        }
    }

    private fun buatCardPesanan(pesanan: PesananModel): View {
        val ctx = requireContext()
        val card = CardView(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 32 }
            radius = 32f
            cardElevation = 8f
        }

        val layout = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(44, 40, 44, 40)
        }

        val tvHeader = TextView(ctx).apply {
            text = "ID #SL-${pesanan.id ?: 0} • ${pesanan.nama_pelanggan}"
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val tvLayanan = TextView(ctx).apply {
            text = "Layanan: ${pesanan.jenis_layanan} (${pesanan.berat_atau_jumlah})"
            setPadding(0, 10, 0, 0)
        }

        val isSelesai = pesanan.status.contains("selesai", ignoreCase = true)
        val tvStatus = TextView(ctx).apply {
            text = "Status: ${pesanan.status}"
            setTextColor(if (isSelesai) Color.parseColor("#4CAF50") else Color.parseColor("#2196F3"))
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 16, 0, 16)
        }

        val rowTombol = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val btnEdit = Button(ctx).apply {
            text = "Edit"
            setOnClickListener { tampilkanDialogPesanan(pesanan) }
        }

        val btnHapus = Button(ctx).apply {
            text = "Hapus"
            setTextColor(Color.RED)
            setOnClickListener {
                pesanan.id?.let { hapusPesanan(it) }
            }
        }

        rowTombol.addView(btnEdit)
        rowTombol.addView(btnHapus)

        layout.addView(tvHeader)
        layout.addView(tvLayanan)
        layout.addView(tvStatus)
        layout.addView(rowTombol)
        card.addView(layout)
        return card
    }

    private fun tampilkanDialogPesanan(pesananEdit: PesananModel?) {
        val ctx = requireContext()
        val layoutForm = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        val etNama = EditText(ctx).apply {
            hint = "Nama Pelanggan (cth: Budi)"
            setText(pesananEdit?.nama_pelanggan ?: "")
        }
        val etLayanan = EditText(ctx).apply {
            hint = "Jenis Layanan (cth: Cuci Komplit)"
            setText(pesananEdit?.jenis_layanan ?: "")
        }
        val etBerat = EditText(ctx).apply {
            hint = "Berat / Jumlah (cth: 3 kg)"
            setText(pesananEdit?.berat_atau_jumlah ?: "")
        }
        val etStatus = EditText(ctx).apply {
            hint = "Status (cth: Sedang Dicuci / Selesai)"
            setText(pesananEdit?.status ?: "Sedang Diproses")
        }

        layoutForm.addView(etNama)
        layoutForm.addView(etLayanan)
        layoutForm.addView(etBerat)
        layoutForm.addView(etStatus)

        AlertDialog.Builder(ctx)
            .setTitle(if (pesananEdit == null) "Tambah Pesanan Baru" else "Edit Pesanan")
            .setView(layoutForm)
            .setPositiveButton("Simpan") { _, _ ->
                val data = PesananModel(
                    nama_pelanggan = etNama.text.toString().trim(),
                    jenis_layanan = etLayanan.text.toString().trim(),
                    berat_atau_jumlah = etBerat.text.toString().trim(),
                    status = etStatus.text.toString().trim()
                )
                if (pesananEdit == null) {
                    simpanPesananBaru(data)
                } else {
                    pesananEdit.id?.let { updatePesanan(it, data) }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun simpanPesananBaru(data: PesananModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.apiService.createPesanan(data)
                Toast.makeText(requireContext(), "Pesanan ditambahkan!", Toast.LENGTH_SHORT).show()
                loadDataPesanan()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal tambah: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updatePesanan(id: Int, data: PesananModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.apiService.updatePesanan("eq.$id", data)
                Toast.makeText(requireContext(), "Pesanan diperbarui!", Toast.LENGTH_SHORT).show()
                loadDataPesanan()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal update: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hapusPesanan(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.apiService.deletePesanan("eq.$id")
                Toast.makeText(requireContext(), "Pesanan dihapus!", Toast.LENGTH_SHORT).show()
                loadDataPesanan()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal hapus: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openTutorial() {
        startActivity(Intent(requireContext(), TutorialMessageActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}