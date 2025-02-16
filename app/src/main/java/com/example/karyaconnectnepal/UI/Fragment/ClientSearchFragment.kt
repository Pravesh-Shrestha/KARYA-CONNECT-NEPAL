package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karyaconnectnepal.Adapter.FreelancerAdapter
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.databinding.FragmentClientSearchBinding


class ClientSearchFragment : Fragment() {
        private lateinit var binding:FragmentClientSearchBinding
        private lateinit var  adapter : FreelancerAdapter
        private val originalfreelancerName = listOf("Raj Shrestha", "Hari Rai")
        private val originalfreelancerjob = listOf("electrician","carpenter")
        private val originalfreelancerImage = listOf(
            R.drawable.electrician,
            R.drawable.carpenter,

        )


        private val filteredfreelancerName = mutableListOf<String>()
        private val filteredfreelancerjob = mutableListOf<String>()
        private val filteredfreelancerImage = mutableListOf<Int>()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding= FragmentClientSearchBinding.inflate(inflater,container,false)
            adapter = FreelancerAdapter(filteredfreelancerName,filteredfreelancerjob,filteredfreelancerImage)
            binding.freelancerRecylerView.layoutManager= LinearLayoutManager(requireContext())
            binding.freelancerRecylerView.adapter=adapter

            // set up for search view

            setupSearchView()
            // show all menu items
            showAllMenu()

            return binding.root
        }

        private fun showAllMenu() {
            filteredfreelancerName.clear()
            filteredfreelancerjob.clear()
            filteredfreelancerImage.clear()


            filteredfreelancerName.addAll(originalfreelancerName)
            filteredfreelancerjob.addAll(originalfreelancerjob)
            filteredfreelancerImage.addAll(originalfreelancerImage)

            adapter.notifyDataSetChanged()


        }

        private fun setupSearchView() {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    filterMenuItems(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filterMenuItems(newText)
                    return true

                }
            })

        }

        private fun filterMenuItems(query: String) {
            filteredfreelancerName.clear()
             filteredfreelancerjob.clear()
            filteredfreelancerImage.clear()

            originalfreelancerName.forEachIndexed { index, itemName ->
                if (itemName.contains(query.toString(),ignoreCase=true)){
                    filteredfreelancerName.add(itemName)
                    filteredfreelancerjob.add(originalfreelancerjob[index])
                    filteredfreelancerImage.add(originalfreelancerImage[index])

                }
            }
            adapter.notifyDataSetChanged()
        }


    }