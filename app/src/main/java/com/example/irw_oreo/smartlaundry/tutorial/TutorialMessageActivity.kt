package com.example.irw_oreo.smartlaundry.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.irw_oreo.databinding.ActivityTutorialMessageBinding
import com.google.android.material.tabs.TabLayoutMediator

class TutorialMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentsList = listOf(
            Tutorial1Fragment(),
            Tutorial2Fragment(),
            Tutorial3Fragment()
        )

        val adapter = TutorialFragmentAdapter(this, fragmentsList)
        binding.tutorialMessageViewPager.adapter = adapter

        // Connecting ViewPager2 with TabLayout indicator dots
        TabLayoutMediator(binding.tabLayoutIndicator, binding.tutorialMessageViewPager) { _, _ -> }.attach()

        binding.btnFinishTutorial.setOnClickListener {
            finish()
        }
    }
}