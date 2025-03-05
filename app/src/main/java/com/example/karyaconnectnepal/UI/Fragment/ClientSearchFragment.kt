package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karyaconnectnepal.Adapter.PortfolioAdapter
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
import com.example.karyaconnectnepal.databinding.FragmentClientSearchBinding

class ClientSearchFragment : Fragment() {

    private var _binding: FragmentClientSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var portfolioViewModel: PortfolioViewModel
    private lateinit var portfolioAdapter: PortfolioAdapter
    private val portfolioList = mutableListOf<PortfolioDisplayModel>()
    private val filteredList = mutableListOf<PortfolioDisplayModel>() // Stores search results

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portfolioViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PortfolioViewModel::class.java]

        portfolioAdapter = PortfolioAdapter(filteredList) { userId ->
            openFreelancerPortfolio(userId)
        }

        binding.freelancerRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.freelancerRecylerView.adapter = portfolioAdapter

        fetchFreelancerPortfolios()

        // Implement Search Feature
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterFreelancers(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterFreelancers(newText ?: "")
                return true
            }
        })
    }

    // Fetch All Freelancer Portfolios
    private fun fetchFreelancerPortfolios() {
        portfolioViewModel.getAllFreelancerPortfolios()
        portfolioViewModel.freelancerPortfolios.observe(viewLifecycleOwner) { freelancers ->
            portfolioList.clear()
            portfolioList.addAll(freelancers)
            filterFreelancers(binding.searchView.query.toString()) // Filter when fetched
        }
    }

    // Filter Freelancers by Job Category
    private fun filterFreelancers(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(portfolioList) // Show all if query is empty
        } else {
            for (freelancer in portfolioList) {
                if (freelancer.jobCategory.contains(query, ignoreCase = true)) {
                    filteredList.add(freelancer)
                }
            }
        }
        portfolioAdapter.notifyDataSetChanged()
    }

    private fun openFreelancerPortfolio(userId: String) {
        val fragment = FreelancerPortfolioFragment().apply {
            arguments = Bundle().apply {
                putString("userId", userId)
            }
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.freelancer_portfolio_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
