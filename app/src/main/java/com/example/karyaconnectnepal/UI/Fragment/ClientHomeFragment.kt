package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karyaconnectnepal.Adapter.PortfolioAdapter
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
import com.example.karyaconnectnepal.databinding.FragmentClientHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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

        fetchLoggedInUserName()

        portfolioViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PortfolioViewModel::class.java]

        portfolioAdapter = PortfolioAdapter(portfolioList) { userId ->
            openFreelancerPortfolio(userId)
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

    private fun fetchLoggedInUserName() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId)

            userRef.child("fullName").get().addOnSuccessListener { snapshot ->
                val userName = snapshot.value as? String
                if (!userName.isNullOrEmpty()) {
                    binding.userNameTextView.text = userName
                } else {
                    binding.userNameTextView.text = "User"
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch user name", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

