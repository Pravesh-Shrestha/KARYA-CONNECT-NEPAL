package com.example.karyaconnectnepal.UI.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.databinding.ActivitySplashPageBinding

class SplashPage : AppCompatActivity() {

    lateinit var binding: ActivitySplashPageBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Initialize View Binding
        binding= ActivitySplashPageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "").orEmpty()
        val password = sharedPreferences.getString("password", "").orEmpty()

        // Delayed transition to the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish() // Optional: Call finish() to remove this activity from the back stack
        }, 2500)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}