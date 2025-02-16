package com.example.karyaconnectnepal.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karyaconnectnepal.databinding.FreelancersBinding


class FreelancerAdapter (private val freelancerName:MutableList<String>,private val freelancerJob:MutableList<String>,private val freelancerimage:MutableList<Int>):
    RecyclerView.Adapter<FreelancerAdapter.FreelancerViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreelancerViewHolder {
        val binding = FreelancersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FreelancerViewHolder(binding)
    }




    override fun onBindViewHolder(holder: FreelancerViewHolder, position: Int) {
        holder.bind(position)

    }
    override fun getItemCount(): Int =freelancerName.size

    inner class FreelancerViewHolder (private val binding: FreelancersBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(position: Int) {
            binding.apply {
                freelancername.text = freelancerName[position]
                    freelancerjob.text = freelancerJob[position]
                freelancerImage.setImageResource(freelancerimage[position])



            }

        }

    }

}