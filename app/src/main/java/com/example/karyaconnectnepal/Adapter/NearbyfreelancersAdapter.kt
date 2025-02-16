package com.example.karyaconnectnepal.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karyaconnectnepal.databinding.NearbyfreelancersBinding

class NearbyfreelancersAdapter (private val name:List<String>, private val job:List<String>, private val image:List<Int>)  : RecyclerView.Adapter<NearbyfreelancersAdapter.NearbyfreelancersHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyfreelancersHolder {
        return NearbyfreelancersHolder(NearbyfreelancersBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: NearbyfreelancersHolder,position: Int){
        val name = name[position]
        val job=job[position]
        val images = image[position]
        holder.bind(name,job,images)
    }

    override fun getItemCount(): Int {
         return name.size
    }



    class NearbyfreelancersHolder(private val binding: NearbyfreelancersBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView = binding.imageView5
        fun bind(name: String, job: String, images: Int) {
            binding.nearbyfreelancer.text = name
            binding.nearbyfreelancerjob.text = job
            imageView.setImageResource(images)

        }


    }
}