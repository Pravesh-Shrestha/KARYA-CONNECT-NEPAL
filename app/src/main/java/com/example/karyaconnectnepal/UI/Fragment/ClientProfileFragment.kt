package com.example.karyaconnectnepal.UI.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.example.karyaconnectnepal.UI.Activity.LoginPage
import com.example.karyaconnectnepal.databinding.FragmentClientProfileBinding
import com.example.karyaconnectnepal.Viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class ClientProfileFragment : Fragment() {

    var _binding: FragmentClientProfileBinding? = null
    val binding get() = _binding!!

    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentClientProfileBinding.inflate(inflater, container, false)
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
                binding.ViewClientFullName.text = user.fullName
                binding.ViewClientEmail.text = user.email
                binding.ViewClientContact.text = user.contact
            } else {
                // Handle the case where user data is not available
                binding.ViewClientFullName.text = "User not found"
                binding.ViewClientEmail.text = "N/A"
                binding.ViewClientContact.text = "N/A"
            }
        }

        // Fetch the logged-in user's data
        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            // Fetch user data from Firebase
            userViewModel.getUserFromDatabase(currentUser.uid)
        } else {
            // Handle the case where no user is logged in
            binding.ViewClientFullName.text = "No user logged in"
            binding.ViewClientEmail.text = "N/A"
            binding.ViewClientContact.text = "N/A"
        }
        binding.freelancerlogoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // Logs out the user

            // Navigate to Login Page
            val intent = Intent(requireContext(), LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



