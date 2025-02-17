package com.example.karyaconnectnepal.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.karyaconnectnepal.UI.Fragment.FreelancerHomeFragment
import com.example.karyaconnectnepal.UI.Fragment.FreelancerNotificationFragment
import com.example.karyaconnectnepal.UI.Fragment.FreelancerProfileFragment
import com.example.karyaconnectnepal.UI.Fragment.PortfolioFormFragment

class FreelancerTabAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return FreelancerHomeFragment()
            1 -> return PortfolioFormFragment()
            2 -> return FreelancerNotificationFragment()
            3 -> return FreelancerProfileFragment()
            else -> return FreelancerHomeFragment()

        }
    }

}