package com.example.karyaconnectnepal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.databinding.ItemFreelancerPortfolioBinding
import com.squareup.picasso.Picasso

class PortfolioAdapter(
    private var portfolioList: List<PortfolioDisplayModel>,
    private val onProfileClick: (String) -> Unit // Callback to handle click navigation
) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    inner class PortfolioViewHolder(private val binding: ItemFreelancerPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(portfolio: PortfolioDisplayModel) {
            binding.freelancerName.text = portfolio.fullName
            binding.freelancerJobCategory.text = portfolio.jobCategory.ifEmpty { "Not Specified" }

            if (portfolio.profileImage.isNotEmpty()) {
                Picasso.get()
                    .load(portfolio.profileImage)
                    .placeholder(R.drawable.profileicon) // Default image
                    .error(R.drawable.profileicon) // Error image
                    .into(binding.freelancerProfileImage) // Make sure this ImageView exists in your XML
            }

            // Handle View Portfolio button click
            binding.viewPortfolioButton.setOnClickListener {
                onProfileClick(portfolio.userId) // Pass userId to navigate
            }

//            binding.root.setOnClickListener { onItemClick(portfolio) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val binding = ItemFreelancerPortfolioBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PortfolioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.bind(portfolioList[position])
    }

    override fun getItemCount(): Int = portfolioList.size

    fun updateData(newList: List<PortfolioDisplayModel>) {
        portfolioList = newList
        notifyDataSetChanged()
    }
}
