package com.example.karyaconnectnepal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.databinding.ItemFreelancerPortfolioBinding

class PortfolioAdapter(
    private var portfolioList: List<PortfolioDisplayModel>,
    private val onItemClick: (PortfolioDisplayModel) -> Unit
) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    inner class PortfolioViewHolder(private val binding: ItemFreelancerPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(portfolio: PortfolioDisplayModel) {
            binding.freelancerName.text = portfolio.fullName
            binding.freelancerJobCategory.text = portfolio.jobCategory

            binding.root.setOnClickListener { onItemClick(portfolio) }
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



