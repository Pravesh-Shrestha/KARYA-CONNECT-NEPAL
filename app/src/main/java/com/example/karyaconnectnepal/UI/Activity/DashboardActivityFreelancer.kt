package com.example.karyaconnectnepal.UI.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.karyaconnectnepal.Adapter.FreelancerTabAdapter
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.databinding.ActivityDashboardFreelancerBinding

class DashboardActivityFreelancer : AppCompatActivity() {

    lateinit var binding: ActivityDashboardFreelancerBinding
    lateinit var adapter: FreelancerTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardFreelancerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FreelancerTabAdapter(supportFragmentManager,lifecycle)
        binding.freelancerdashView.adapter = adapter

        binding.freelancerbottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.freelancernavHome -> binding.freelancerdashView.setCurrentItem(0, false)
                R.id.freelancernavAdd -> binding.freelancerdashView.setCurrentItem(1, false)
                R.id.freelancernavNotification -> binding.freelancerdashView.setCurrentItem(2, false)
                R.id.freelancernavProfile -> binding.freelancerdashView.setCurrentItem(3, false)
                else -> false
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}