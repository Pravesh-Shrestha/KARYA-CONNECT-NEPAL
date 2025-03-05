//package com.example.karyaconnectnepal.UI.Fragment
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.karyaconnectnepal.R
//import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
//import com.example.karyaconnectnepal.ViewModel.ProjectViewModel
//import com.example.karyaconnectnepal.databinding.FragmentFreelancerPortfolioBinding
//import com.google.firebase.database.FirebaseDatabase
//import com.squareup.picasso.Picasso
//
//class FreelancerPortfolioFragment : Fragment() {
//
//    private var _binding: FragmentFreelancerPortfolioBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var portfolioViewModel: PortfolioViewModel
//    private lateinit var projectViewModel: ProjectViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentFreelancerPortfolioBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        portfolioViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PortfolioViewModel::class.java]
//        projectViewModel = ViewModelProvider(this)[ProjectViewModel::class.java]
//
//        // Check if userId is received
//        val userId = arguments?.getString(ARG_USER_ID) ?: ""
//        if (userId.isEmpty()) {
//            Log.e("FreelancerPortfolio", "No userId received!")
//            return
//        }
//        fetchFreelancerProfileImage(userId)
//        Log.d("FreelancerPortfolio", "Fetching data for userId: $userId")
//
//        // Show Loading
////        binding.progressBar.visibility = View.VISIBLE
//
//        // Fetch freelancer portfolio
//        portfolioViewModel.loadFreelancerPortfolio(userId)
//
//        // Observe data and update UI
//        portfolioViewModel.portfolio.observe(viewLifecycleOwner) { portfolio ->
////            binding.progressBar.visibility = View.GONE // Hide Loading
//            if (portfolio == null) {
//                Log.e("FreelancerPortfolio", "No portfolio data found in Firebase")
//                return@observe
//            }
//
//            Log.d("FreelancerPortfolio", "Portfolio Data: $portfolio")
//                binding.freelancerName.text = portfolio.personalInfo["fullName"] as? String ?: "N/A"
//                binding.freelancerEmail.text = portfolio.personalInfo["email"] as? String ?: "N/A"
//                binding.freelancerContact.text = portfolio.personalInfo["contact"] as? String ?: "N/A"
//                binding.freelancerEducation.text = portfolio.education
//                binding.freelancerSkills.text = portfolio.skills.joinToString(", ")
//                binding.freelancerCertifications.text = portfolio.certifications.joinToString(", ")
//                binding.freelancerExperience.text = portfolio.experience
//                binding.freelancerJobCategory.text = portfolio.jobCategory
//                binding.freelancerPortfolioDescription.text = portfolio.portfolioDescription
//
//        }
//        // ✅ Set up RecyclerView for projects
//        setupProjectRecyclerView()
//
//        // ✅ Fetch freelancer projects
//        projectViewModel.getFreelancerProjects(userId)
//
//        // ✅ Observe projects
//        projectViewModel.projectList.observe(viewLifecycleOwner) { projects ->
//            projectList.clear()
//            projectList.addAll(projects)
//            projectAdapter.notifyDataSetChanged()
//        }
//    }
//
//
//    private fun fetchFreelancerProfileImage(userId: String) {
//        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId)
//
//        userRef.child("profileImage").get().addOnSuccessListener { snapshot ->
//            val profileImageUrl = snapshot.value as? String
//
//            if (!profileImageUrl.isNullOrEmpty()) {
//                Picasso.get()
//                    .load(profileImageUrl)
//                    .placeholder(R.drawable.profileicon) // Default placeholder image
//                    .error(R.drawable.profileicon) // If loading fails
//                    .into(binding.freelancerProfileImage) // Make sure this ImageView exists in XML
//            }
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(), "Failed to load profile image", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        private const val ARG_USER_ID = "user_id"
//        fun newInstance(userId: String): FreelancerPortfolioFragment {
//            val fragment = FreelancerPortfolioFragment()
//            val args = Bundle()
//            args.putString(ARG_USER_ID, userId)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//}


package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karyaconnectnepal.Adapter.ProjectAdapter
import com.example.karyaconnectnepal.Model.ProjectModel
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
import com.example.karyaconnectnepal.ViewModel.ProjectViewModel
import com.example.karyaconnectnepal.databinding.FragmentFreelancerPortfolioBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class FreelancerPortfolioFragment : Fragment() {

    private var _binding: FragmentFreelancerPortfolioBinding? = null
    private val binding get() = _binding!!

    private lateinit var portfolioViewModel: PortfolioViewModel
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var projectAdapter: ProjectAdapter

    private val projectList = mutableListOf<ProjectModel>() // List to hold projects

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
        projectViewModel = ViewModelProvider(this)[ProjectViewModel::class.java]

        val userId = arguments?.getString(ARG_USER_ID) ?: ""
        if (userId.isEmpty()) {
            Log.e("FreelancerPortfolio", "No userId received!")
            return
        }

        fetchFreelancerProfileImage(userId)

        // Fetch freelancer portfolio
        portfolioViewModel.loadFreelancerPortfolio(userId)

        // Observe portfolio data
        portfolioViewModel.portfolio.observe(viewLifecycleOwner) { portfolio ->
            if (portfolio == null) {
                Log.e("FreelancerPortfolio", "No portfolio data found in Firebase")
                return@observe
            }

            // Update UI with portfolio details
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

        // ✅ Set up RecyclerView for projects
        setupProjectRecyclerView()

        // ✅ Fetch freelancer projects
        projectViewModel.getProjects(userId)

        // ✅ Observe projects
        projectViewModel._projectList.observe(viewLifecycleOwner) { projects ->
            projectList.clear()
            projectList.addAll(projects)
            projectAdapter.notifyDataSetChanged()
        }
    }

    private fun fetchFreelancerProfileImage(userId: String) {
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId)

        userRef.child("profileImage").get().addOnSuccessListener { snapshot ->
            val profileImageUrl = snapshot.value as? String

            if (!profileImageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(profileImageUrl)
                    .placeholder(R.drawable.profileicon)
                    .error(R.drawable.profileicon)
                    .into(binding.freelancerProfileImage)
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to load profile image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupProjectRecyclerView() {
        projectAdapter = ProjectAdapter(projectList)
        binding.projectRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.projectRecyclerView.adapter = projectAdapter
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
