package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karyaconnectnepal.Adapter.NearbyfreelancersAdapter
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.databinding.FragmentClientHomeBinding


class ClientHomeFragment : Fragment() {
    private lateinit var binding: FragmentClientHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val freelancerName = listOf("Raj Shrestha", "Hari Rai")
        val freelancerJob = listOf("Electrician", "Carpenter")
        val nearbyfreelancerImage = listOf(
            R.drawable.electrician,
            R.drawable.carpenter
        )
        val adapter = NearbyfreelancersAdapter(freelancerName, freelancerJob, nearbyfreelancerImage)
        binding.nearRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.nearRecyclerView.adapter = adapter


    }

}