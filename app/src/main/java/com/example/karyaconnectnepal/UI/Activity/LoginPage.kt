package com.example.karyaconnectnepal.UI.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.example.karyaconnectnepal.Viewmodel.UserViewModel
import com.example.karyaconnectnepal.databinding.ActivityDashboardClientBinding
import com.example.karyaconnectnepal.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {

    lateinit var binding: ActivityLoginPageBinding
    lateinit var userViewModel: UserViewModel

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImplementation()
        userViewModel = UserViewModel(repo)

        binding.buttonLogin.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passText.text.toString()

            // Check for empty fields
            if (email.isEmpty()) {
                binding.emailText.error = "Please fill the email field"
                return@setOnClickListener
            }

            // Check for empty password field
            if (password.isEmpty()) {
                binding.passText.error = "Please fill the password field"
                return@setOnClickListener
            }

            // Check if a user role is selected
            if (!binding.radioClient.isChecked && !binding.radioFreelancer.isChecked) {
                Toast.makeText(this@LoginPage, "Please select a user type", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            userViewModel.login(email, password) { success,message,userType ->
                if (success) {
                    if (userType == "Client") {
                        Toast.makeText(this@LoginPage, message, Toast.LENGTH_LONG).show()
                        intent = Intent(this@LoginPage, DashboardActivityClient::class.java)
                        startActivity(intent)
                    } else if (userType == "Freelancer") {
                        Toast.makeText(this@LoginPage, message, Toast.LENGTH_LONG).show()

                        // Navigate to the freelancer dashboard
                        intent = Intent(this@LoginPage, DashboardActivityFreelancer::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@LoginPage, message, Toast.LENGTH_LONG).show()
                }
            }
        }


        binding.forgotPassword.setOnClickListener {
            val email = binding.emailText.text.toString()

            userViewModel.forgetPassword(email) { success, message ->
                Toast.makeText(this@LoginPage, message, Toast.LENGTH_LONG).show()
            }
        }

        // Set up the listener for the registration text view
        binding.registerButton.setOnClickListener {
            navigateToRegistration()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }// Closing bracket for onCreate

    private fun navigateToRegistration() {
        val intent = Intent(this@LoginPage, RegistrationActivity::class.java)
        startActivity(intent)
    }
}



