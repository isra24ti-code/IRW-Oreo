package com.example.irw_oreo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.irw_oreo.databinding.FragmentHomeBinding
import com.example.irw_oreo.pertemuan_10.TenthActivity
import com.example.irw_oreo.smartlaundry.DetailActivity
import com.example.irw_oreo.smartlaundry.LoginActivity
import com.example.irw_oreo.smartlaundry.ProfileActivity
import com.example.irw_oreo.smartlaundry.ServiceAdapter
import com.example.irw_oreo.smartlaundry.ServiceModel
import com.example.irw_oreo.smartlaundry.WebActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Toolbar pada Fragment
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"

        // Setup Grid RecyclerView Layanan Unggulan
        setupServicesGrid()

        // Tombol Pertemuan 10 & 11
        binding.btnPertemuan10.setOnClickListener {
            startActivity(Intent(requireContext(), TenthActivity::class.java))
        }

        // Tombol Lihat Detail
        binding.btnDetail.setOnClickListener {
            startActivity(Intent(requireContext(), DetailActivity::class.java))
        }

        // Tombol Buka Web
        binding.btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), WebActivity::class.java))
        }

        // Tombol Profil Developer
        binding.btnProfile.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            val prefManager = PreferencesManager(requireContext())
            prefManager.clearSession()

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    private fun setupServicesGrid() {
        val services = listOf(
            ServiceModel(1, "Cuci Komplit", "Rp 7.000 / kg", "Reguler", R.drawable.ic_laundry_basket),
            ServiceModel(2, "Setrika Express", "Rp 12.000 / kg", "Kilat", R.drawable.ic_iron),
            ServiceModel(3, "Dry Cleaning", "Rp 25.000 / pcs", "Special", R.drawable.ic_dry_clean),
            ServiceModel(4, "Antar-Jemput", "GRATIS", "Promo", R.drawable.ic_delivery),
            ServiceModel(5, "Cuci Sepatu", "Rp 30.000 / pasang", "Care", R.drawable.ic_laundry_basket),
            ServiceModel(6, "Cuci Karpet", "Rp 15.000 / m²", "Big Item", R.drawable.ic_iron)
        )

        val adapter = ServiceAdapter(services) { selectedService ->
            Toast.makeText(
                requireContext(),
                "Anda memilih layanan: ${selectedService.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvServices.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}