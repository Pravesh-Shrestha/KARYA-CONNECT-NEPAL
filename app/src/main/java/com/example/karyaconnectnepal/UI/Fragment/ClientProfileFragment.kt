//package com.example.karyaconnectnepal.UI.Fragment
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
//import com.example.karyaconnectnepal.UI.Activity.LoginPage
//import com.example.karyaconnectnepal.databinding.FragmentClientProfileBinding
//import com.example.karyaconnectnepal.Viewmodel.UserViewModel
//import com.google.firebase.auth.FirebaseAuth
//
//class ClientProfileFragment : Fragment() {
//
//    var _binding: FragmentClientProfileBinding? = null
//    val binding get() = _binding!!
//
//    lateinit var userViewModel: UserViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout using View Binding
//        _binding = FragmentClientProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize UserViewModel directly without factory
//        userViewModel = UserViewModel(UserRepositoryImplementation())
//
//        // Observe user data
//        userViewModel.user.observe(viewLifecycleOwner) { user ->
//            if (user != null) {
//                // Set the user data in the UI
//                binding.ViewClientFullName.text = user.fullName
//                binding.ViewClientEmail.text = user.email
//                binding.ViewClientContact.text = user.contact
//            } else {
//                // Handle the case where user data is not available
//                binding.ViewClientFullName.text = "User not found"
//                binding.ViewClientEmail.text = "N/A"
//                binding.ViewClientContact.text = "N/A"
//            }
//        }
//
//        // Fetch the logged-in user's data
//        val currentUser = userViewModel.getCurrentUser()
//        if (currentUser != null) {
//            // Fetch user data from Firebase
//            userViewModel.getUserFromDatabase(currentUser.uid)
//        } else {
//            // Handle the case where no user is logged in
//            binding.ViewClientFullName.text = "No user logged in"
//            binding.ViewClientEmail.text = "N/A"
//            binding.ViewClientContact.text = "N/A"
//        }
//        binding.freelancerlogoutBtn.setOnClickListener {
//            FirebaseAuth.getInstance().signOut() // Logs out the user
//
//            // Navigate to Login Page
//            val intent = Intent(requireContext(), LoginPage::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//
//
//


//package com.example.karyaconnectnepal.UI.Fragment
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
//import com.example.karyaconnectnepal.UI.Activity.LoginPage
//import com.example.karyaconnectnepal.Viewmodel.UserViewModel
//import com.example.karyaconnectnepal.databinding.FragmentFreelancerProfileBinding
//import com.google.firebase.auth.FirebaseAuth
//
//class FreelancerProfileFragment : Fragment() {
//
//    var _binding: FragmentFreelancerProfileBinding? = null
//    val binding get() = _binding!!
//
//    lateinit var userViewModel: UserViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout using View Binding
//        _binding = FragmentFreelancerProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize UserViewModel directly without factory
//        userViewModel = UserViewModel(UserRepositoryImplementation())
//
//        // Observe user data
//        userViewModel.user.observe(viewLifecycleOwner) { user ->
//            if (user != null) {
//                // Set the user data in the UI
//                binding.ViewFreelancerFullName.text = user.fullName
//                binding.textViewEmail.text = user.email
//                binding.textViewContact.text = user.contact
//            } else {
//                // Handle the case where user data is not available
//                binding.ViewFreelancerFullName.text = "User not found"
//                binding.textViewEmail.text = "N/A"
//                binding.textViewContact.text = "N/A"
//            }
//        }
//
//        // Fetch the logged-in user's data
//        val currentUser = userViewModel.getCurrentUser()
//        if (currentUser != null) {
//            // Fetch user data from Firebase
//            userViewModel.getUserFromDatabase(currentUser.uid)
//        } else {
//            // Handle the case where no user is logged in
//            binding.ViewFreelancerFullName.text = "No user logged in"
//            binding.textViewEmail.text = "N/A"
//            binding.textViewContact.text = "N/A"
//        }
//        binding.clientlogoutbtn.setOnClickListener {
//            FirebaseAuth.getInstance().signOut() // Logs out the user
//
//            // Navigate to Login Page
//            val intent = Intent(requireContext(), LoginPage::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
//
//        binding.imageBrowse.setOnClickListener {
//            openImagePicker()
//        }
//
//    }
//
//    private fun openImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_REQUEST)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//
//
//


package com.example.karyaconnectnepal.UI.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.example.karyaconnectnepal.UI.Activity.LoginPage
import com.example.karyaconnectnepal.Viewmodel.CommonViewModel
import com.example.karyaconnectnepal.Viewmodel.UserViewModel
import com.example.karyaconnectnepal.databinding.FragmentClientProfileBinding
import com.example.karyaconnectnepal.databinding.FragmentFreelancerProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ClientProfileFragment : Fragment() {

    private var _binding: FragmentClientProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var commonViewModel: CommonViewModel
    private val userRepository = UserRepositoryImplementation() // Initialize UserRepository
    private var selectedImageUri: Uri? = null  // Variable to store selected image

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonViewModel = ViewModelProvider(this)[CommonViewModel::class.java]

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userRef = FirebaseDatabase.getInstance().reference.child("users").child(currentUser.uid)

            userRef.get().addOnSuccessListener { snapshot ->
                val fullName = snapshot.child("fullName").value as? String ?: "N/A"
                val email = snapshot.child("email").value as? String ?: "N/A"
                val contact = snapshot.child("contact").value as? String ?: "N/A"
                val profileImageUrl = snapshot.child("profileImage").value as? String ?: ""

                binding.ViewClientFullName.text = fullName
                binding.ViewClientEmail.text = email
                binding.ViewClientContact.text = contact

                // ✅ Debugging: Log the Fetched Profile Image URL
                android.util.Log.d("FIREBASE_PROFILE", "Fetched Image URL: $profileImageUrl")

                // ✅ Ensure Profile Image URL is Not Empty Before Loading
                if (profileImageUrl.isNotEmpty()) {
                    Picasso.get()
                        .load(profileImageUrl)
                        .placeholder(R.drawable.profileicon) // Default profile image
                        .error(R.drawable.profileicon) // Error image
                        .into(binding.imageBrowse)
                } else {
                    android.util.Log.e("FIREBASE_PROFILE", "Profile Image URL is empty")
                }
            }.addOnFailureListener {
                android.util.Log.e("FIREBASE_PROFILE", "Failed to fetch profile image: ${it.message}")
            }
        }



        // ✅ Select Image
        binding.editProfileImage.setOnClickListener {
            openImagePicker()
        }

        // ✅ Upload Image
        binding.buttonEditProfile.setOnClickListener {
            selectedImageUri?.let {
                uploadProfileImage(it)
            } ?: Toast.makeText(requireContext(), "Please select an image first!", Toast.LENGTH_SHORT).show()
        }



        // ✅ Logout Button
        binding.clientlogoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    // Opens Gallery for Image Selection
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_REQUEST)
    }

    // Handles the Image Selection Result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            if (selectedImageUri != null) {
                binding.imageBrowse.setImageURI(selectedImageUri)  // Show selected image
                Toast.makeText(requireContext(), "Image Selected!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No Image Selected!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // ✅ Upload Image to Cloudinary and Save URL in Firebase
    private fun uploadProfileImage(imageUri: Uri) {
        commonViewModel.uploadProfileImage(requireContext(), imageUri) { success, imageUrl ->
            if (success) {
                // ✅ Update Firebase with New Profile Image
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let { user ->
                    FirebaseDatabase.getInstance().reference.child("users").child(user.uid)
                        .child("profileImage").setValue(imageUrl)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Profile Image Updated!", Toast.LENGTH_SHORT).show()
                            // ✅ Update UI
                            Picasso.get().load(imageUrl).into(binding.imageBrowse)
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Failed to update Firebase!", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(requireContext(), "Upload Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IMAGE_PICK_REQUEST = 100
    }
}


