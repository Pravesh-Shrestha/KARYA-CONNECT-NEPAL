package com.example.karyaconnectnepal.UI.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.UI.Fragment.ClientHomeFragment
import com.example.karyaconnectnepal.UI.Fragment.ClientNotificationFragment
import com.example.karyaconnectnepal.UI.Fragment.ClientProfileFragment
import com.example.karyaconnectnepal.UI.Fragment.ClientSearchFragment
import com.example.karyaconnectnepal.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ClientHomeFragment())
        binding.bottomView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.clientnavHome -> replaceFragment(ClientHomeFragment())
                R.id.clientnavSearch->replaceFragment(ClientSearchFragment())
                R.id.clientnavNotification ->replaceFragment(ClientNotificationFragment())
                R.id.clientnavProfile ->replaceFragment(ClientProfileFragment())
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(Fragment: Fragment) {
        val fragmentManager : FragmentManager =
            supportFragmentManager
        val fragmentTransaction : FragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameBottom.id,
            Fragment)
        fragmentTransaction.commit()

    }
}

