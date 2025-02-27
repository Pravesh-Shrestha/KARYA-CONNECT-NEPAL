package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.example.karyaconnectnepal.Viewmodel.UserViewModel
import com.example.karyaconnectnepal.databinding.FragmentFreelancerProfileBinding

class FreelancerProfileFragment : Fragment() {

    var _binding: FragmentFreelancerProfileBinding? = null
    val binding get() = _binding!!

    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentFreelancerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UserViewModel directly without factory
        userViewModel = UserViewModel(UserRepositoryImplementation())

        // Observe user data
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // Set the user data in the UI
                binding.ViewFreelancerFullName.text = user.fullName
                binding.textViewEmail.text = user.email
                binding.textViewContact.text = user.contact
            } else {
                // Handle the case where user data is not available
                binding.ViewFreelancerFullName.text = "User not found"
                binding.textViewEmail.text = "N/A"
                binding.textViewContact.text = "N/A"
            }
        }

        // Fetch the logged-in user's data
        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            // Fetch user data from Firebase
            userViewModel.getUserFromDatabase(currentUser.uid)
        } else {
            // Handle the case where no user is logged in
            binding.ViewFreelancerFullName.text = "No user logged in"
            binding.textViewEmail.text = "N/A"
            binding.textViewContact.text = "N/A"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



