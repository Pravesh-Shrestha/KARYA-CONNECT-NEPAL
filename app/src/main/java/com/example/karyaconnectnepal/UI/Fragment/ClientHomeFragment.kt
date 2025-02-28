//package com.example.karyaconnectnepal.UI.Fragment
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.karyaconnectnepal.Adapter.NearbyfreelancersAdapter
//import com.example.karyaconnectnepal.R
//import com.example.karyaconnectnepal.databinding.FragmentClientHomeBinding
//
//
//class ClientHomeFragment : Fragment() {
//    private lateinit var binding: FragmentClientHomeBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentClientHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val freelancerName = listOf("Raj Shrestha", "Hari Rai","Raj Shrestha", "Hari Rai","Raj Shrestha", "Hari Rai")
//        val freelancerJob = listOf("Electrician", "Carpenter","Electrician", "Carpenter","Electrician", "Carpenter")
//        val nearbyfreelancerImage = listOf(
//            R.drawable.electrician,
//            R.drawable.carpenter ,
//            R.drawable.electrician,
//            R.drawable.carpenter,
//            R.drawable.electrician,
//            R.drawable.carpenter
//        )
//        val adapter = NearbyfreelancersAdapter(freelancerName, freelancerJob, nearbyfreelancerImage)
//        binding.nearRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.nearRecyclerView.adapter = adapter
//
//
//    }
//
//}

package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.karyaconnectnepal.Adapter.PortfolioAdapter
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.UI.Activity.DashboardActivityFreelancer
import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
import com.example.karyaconnectnepal.databinding.FragmentClientHomeBinding
import com.google.firebase.auth.FirebaseAuth

class ClientHomeFragment : Fragment() {

    private var _binding: FragmentClientHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var portfolioViewModel: PortfolioViewModel
    private lateinit var portfolioAdapter: PortfolioAdapter
    private val portfolioList = mutableListOf<PortfolioDisplayModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portfolioViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PortfolioViewModel::class.java]

        portfolioAdapter = PortfolioAdapter(portfolioList) { portfolio ->
            openFreelancerPortfolio(portfolio.userId)
        }

        binding.freelancerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.freelancerRecyclerView.adapter = portfolioAdapter

        fetchFreelancerPortfolios()
    }

    private fun fetchFreelancerPortfolios() {
        portfolioViewModel.getAllFreelancerPortfolios()
        portfolioViewModel.freelancerPortfolios.observe(viewLifecycleOwner) { freelancers ->
            portfolioList.clear()
            portfolioList.addAll(freelancers)
            portfolioAdapter.notifyDataSetChanged()
        }
    }

    private fun openFreelancerPortfolio(userId: String) {

        val fragment = FreelancerPortfolioFragment.newInstance(userId)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.freelancer_portfolio_container, fragment)
            .addToBackStack(null) // Allows back navigation
            .commit()

        requireActivity().findViewById<View>(R.id.freelancer_portfolio_container)?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

