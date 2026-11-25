package com.example.irw_oreo

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.irw_oreo.databinding.FragmentMoreBinding
import kotlinx.coroutines.launch

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol Tambah Layanan (CREATE)
        binding.btnTambahLayanan.setOnClickListener {
            tampilkanDialogLayanan(null)
        }

        // Muat daftar layanan dari Supabase (READ)
        loadDataLayanan()
    }

    private fun loadDataLayanan() {
        binding.tvInfoLayanan.visibility = View.VISIBLE
        binding.tvInfoLayanan.text = "Memuat daftar layanan..."

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val daftarLayanan = SupabaseClient.apiService.getAllLayanan()
                binding.containerLayanan.removeAllViews()

                if (daftarLayanan.isEmpty()) {
                    binding.tvInfoLayanan.visibility = View.VISIBLE
                    binding.tvInfoLayanan.text = "Belum ada layanan. Klik '+ Layanan' untuk menambah."
                } else {
                    binding.tvInfoLayanan.visibility = View.GONE
                    daftarLayanan.forEachIndexed { index, item ->
                        binding.containerLayanan.addView(buatCardLayanan(index + 1, item))
                    }
                }
            } catch (e: Exception) {
                binding.tvInfoLayanan.visibility = View.VISIBLE
                binding.tvInfoLayanan.text = "Gagal memuat layanan: ${e.message}"
            }
        }
    }

    private fun buatCardLayanan(nomor: Int, layanan: LayananModel): View {
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

        // Baris Atas: Nama Layanan (Kiri) & Badge Harga (Kanan)
        val topRow = RelativeLayout(ctx).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val tvNama = TextView(ctx).apply {
            text = "$nomor. ${layanan.nama_layanan}"
            textSize = 16f
            setTextColor(Color.parseColor("#212121"))
            setTypeface(null, Typeface.BOLD)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply { addRule(RelativeLayout.ALIGN_PARENT_START) }
        }

        val tvHarga = TextView(ctx).apply {
            text = layanan.harga
            textSize = 13f
            setTextColor(Color.parseColor("#2E7D32"))
            setBackgroundColor(Color.parseColor("#E8F5E9"))
            setTypeface(null, Typeface.BOLD)
            setPadding(24, 10, 24, 10)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply { addRule(RelativeLayout.ALIGN_PARENT_END) }
        }

        topRow.addView(tvNama)
        topRow.addView(tvHarga)

        // Kategori Layanan
        val tvKategori = TextView(ctx).apply {
            text = "Kategori: ${layanan.kategori}"
            textSize = 14f
            setTextColor(Color.parseColor("#616161"))
            setPadding(0, 18, 0, 16)
        }

        // Baris Tombol Edit & Hapus
        val rowTombol = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val btnEdit = Button(ctx).apply {
            text = "Edit"
            setOnClickListener { tampilkanDialogLayanan(layanan) }
        }

        val btnHapus = Button(ctx).apply {
            text = "Hapus"
            setTextColor(Color.RED)
            setOnClickListener {
                layanan.id?.let { hapusLayanan(it) }
            }
        }

        rowTombol.addView(btnEdit)
        rowTombol.addView(btnHapus)

        layout.addView(topRow)
        layout.addView(tvKategori)
        layout.addView(rowTombol)
        card.addView(layout)
        return card
    }

    private fun tampilkanDialogLayanan(layananEdit: LayananModel?) {
        val ctx = requireContext()
        val layoutForm = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
        }

        val etNama = EditText(ctx).apply {
            hint = "Nama Layanan (cth: Cuci Setrika)"
            setText(layananEdit?.nama_layanan ?: "")
        }
        val etKategori = EditText(ctx).apply {
            hint = "Kategori / Keterangan (cth: Reguler / Kilat)"
            setText(layananEdit?.kategori ?: "")
        }
        val etHarga = EditText(ctx).apply {
            hint = "Harga (cth: Rp 9.000 / kg)"
            setText(layananEdit?.harga ?: "")
        }

        layoutForm.addView(etNama)
        layoutForm.addView(etKategori)
        layoutForm.addView(etHarga)

        AlertDialog.Builder(ctx)
            .setTitle(if (layananEdit == null) "Tambah Layanan Baru" else "Edit Layanan")
            .setView(layoutForm)
            .setPositiveButton("Simpan") { _, _ ->
                val data = LayananModel(
                    nama_layanan = etNama.text.toString().trim(),
                    kategori = etKategori.text.toString().trim(),
                    harga = etHarga.text.toString().trim()
                )
                if (layananEdit == null) {
                    simpanLayananBaru(data)
                } else {
                    layananEdit.id?.let { updateLayanan(it, data) }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun simpanLayananBaru(data: LayananModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.apiService.createLayanan(data)
                Toast.makeText(requireContext(), "Layanan ditambahkan!", Toast.LENGTH_SHORT).show()
                loadDataLayanan()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal tambah: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateLayanan(id: Int, data: LayananModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.apiService.updateLayanan("eq.$id", data)
                Toast.makeText(requireContext(), "Layanan diperbarui!", Toast.LENGTH_SHORT).show()
                loadDataLayanan()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal update: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hapusLayanan(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                SupabaseClient.apiService.deleteLayanan("eq.$id")
                Toast.makeText(requireContext(), "Layanan dihapus!", Toast.LENGTH_SHORT).show()
                loadDataLayanan()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Gagal hapus: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}