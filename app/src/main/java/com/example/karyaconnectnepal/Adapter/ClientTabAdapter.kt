package com.example.karyaconnectnepal.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.karyaconnectnepal.UI.Fragment.ClientHomeFragment
import com.example.karyaconnectnepal.UI.Fragment.ClientNotificationFragment
import com.example.karyaconnectnepal.UI.Fragment.ClientProfileFragment
import com.example.karyaconnectnepal.UI.Fragment.ClientSearchFragment

class ClientTabAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return ClientHomeFragment()
            1 -> return ClientSearchFragment()
            2 -> return ClientNotificationFragment()
            3 -> return ClientProfileFragment()
            else -> return ClientHomeFragment()

        }
    }

}