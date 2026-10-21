package com.example.irw_oreo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.irw_oreo.databinding.FragmentHomeBinding
import com.example.irw_oreo.smartlaundry.DetailActivity
import com.example.irw_oreo.smartlaundry.LoginActivity
import com.example.irw_oreo.smartlaundry.ProfileActivity
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}