package com.example.karyaconnectnepal.UI.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.karyaconnectnepal.Adapter.ClientTabAdapter
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.databinding.ActivityDashboardClientBinding

class DashboardActivityClient : AppCompatActivity() {

    lateinit var binding: ActivityDashboardClientBinding
    lateinit var adapter: ClientTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ClientTabAdapter(supportFragmentManager, lifecycle)
        binding.clientdashView.adapter = adapter

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            hideFreelancerPortfolio() // Ensures that the freelancer portfolio is hidden when switching

            when (item.itemId) {
                R.id.clientnavHome -> binding.clientdashView.setCurrentItem(0, false)
                R.id.clientnavSearch -> binding.clientdashView.setCurrentItem(1, false)
                R.id.clientnavNotification -> binding.clientdashView.setCurrentItem(2, false)
                R.id.clientnavProfile -> binding.clientdashView.setCurrentItem(3, false)
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

    private fun hideFreelancerPortfolio() {
        val portfolioFragment = supportFragmentManager.findFragmentById(R.id.freelancer_portfolio_container)
        portfolioFragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        binding.freelancerPortfolioContainer.visibility = View.GONE
    }
}
