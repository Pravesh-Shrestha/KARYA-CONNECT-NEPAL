package com.example.karyaconnectnepal.UI.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.Adapter.ClientTabAdapter
import com.example.karyaconnectnepal.databinding.ActivityDashboardClientBinding
import com.google.android.material.tabs.TabLayoutMediator

class DashboardActivityClient : AppCompatActivity() {

    lateinit var clientdashBinding: ActivityDashboardClientBinding

    lateinit var adapter: ClientTabAdapter

    //If tab ko header ma text rakhne bhaye
    var data = arrayOf("Home", "Search", "Notification", "Profile")

    //If tab ko header ma icon rakhne bhaye
    var icons = arrayOf(R.drawable.home, R.drawable.search,
        R.drawable.notification, R.drawable.profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        clientdashBinding = ActivityDashboardClientBinding.inflate(layoutInflater)
        setContentView(clientdashBinding.root)

        adapter = ClientTabAdapter(supportFragmentManager,lifecycle)
        clientdashBinding.clientdashView.adapter = adapter


        TabLayoutMediator(clientdashBinding.clientTabLayout,clientdashBinding.clientdashView) {

            //If you want text
//            tabs, position -> tabs.text = data[position]
//        }.attach()

           // If you want icons
                tabs, position ->
            tabs.icon = resources.getDrawable(icons[position], null)
        }.attach()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}