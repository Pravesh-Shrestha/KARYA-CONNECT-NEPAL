package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
import com.example.karyaconnectnepal.databinding.FragmentFreelancerPortfolioBinding

class FreelancerPortfolioFragment : Fragment() {

    private var _binding: FragmentFreelancerPortfolioBinding? = null
    private val binding get() = _binding!!
    private lateinit var portfolioViewModel: PortfolioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFreelancerPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portfolioViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PortfolioViewModel::class.java]

        // Check if userId is received
        val userId = arguments?.getString(ARG_USER_ID) ?: ""
        if (userId.isEmpty()) {
            Log.e("FreelancerPortfolio", "No userId received!")
            return
        }

        Log.d("FreelancerPortfolio", "Fetching data for userId: $userId")

        // Show Loading
//        binding.progressBar.visibility = View.VISIBLE

        // Fetch freelancer portfolio
        portfolioViewModel.loadFreelancerPortfolio(userId)

        // Observe data and update UI
        portfolioViewModel.portfolio.observe(viewLifecycleOwner) { portfolio ->
//            binding.progressBar.visibility = View.GONE // Hide Loading
            if (portfolio == null) {
                Log.e("FreelancerPortfolio", "No portfolio data found in Firebase")
                return@observe
            }

            Log.d("FreelancerPortfolio", "Portfolio Data: $portfolio")
                binding.freelancerName.text = portfolio.personalInfo["fullName"] as? String ?: "N/A"
                binding.freelancerEmail.text = portfolio.personalInfo["email"] as? String ?: "N/A"
                binding.freelancerContact.text = portfolio.personalInfo["contact"] as? String ?: "N/A"
                binding.freelancerEducation.text = portfolio.education
                binding.freelancerSkills.text = portfolio.skills.joinToString(", ")
                binding.freelancerCertifications.text = portfolio.certifications.joinToString(", ")
                binding.freelancerExperience.text = portfolio.experience
                binding.freelancerJobCategory.text = portfolio.jobCategory
                binding.freelancerPortfolioDescription.text = portfolio.portfolioDescription

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_USER_ID = "user_id"
        fun newInstance(userId: String): FreelancerPortfolioFragment {
            val fragment = FreelancerPortfolioFragment()
            val args = Bundle()
            args.putString(ARG_USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }
}
