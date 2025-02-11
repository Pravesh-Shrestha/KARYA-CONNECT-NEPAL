package com.example.karyaconnectnepal.UI.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.karyaconnectnepal.Model.UserModel
import com.example.karyaconnectnepal.R
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.example.karyaconnectnepal.Viewmodel.UserViewModel
import com.example.karyaconnectnepal.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    lateinit var registrationBinding : ActivityRegistrationBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        registrationBinding = ActivityRegistrationBinding.inflate(layoutInflater)

        setContentView(registrationBinding.root)

        var repo = UserRepositoryImplementation()
        userViewModel = UserViewModel(repo)

        registrationBinding.registerButtn.setOnClickListener{
            var email = registrationBinding.regemailText.text.toString()
            var password = registrationBinding.regpasswordText.text.toString()
            var fullName = registrationBinding.regfullnameText.text.toString()
            var Contact = registrationBinding.regcontactText.text.toString()

            var userType = ""
            if(registrationBinding.regclientRadio.isChecked){
                userType = "Client"
            }else{
                userType = "Freelancer"
            }

            if(userType.isEmpty()) {
            registrationBinding.regclientRadio.error = "Please, Select a User "
            registrationBinding.regfreelancerRadio.error = "Please, Select a User "
            }else if(fullName.isEmpty()){
                registrationBinding.regfullnameText.error = "Please, Enter your Full Name"

            }else if(email.isEmpty()){
                registrationBinding.regemailText.error = "Please, Enter your email"

            }else if(password.isEmpty()){
                registrationBinding.regpasswordText.error = "Please, Enter password "

            }else if(Contact.isEmpty()){
                registrationBinding.regcontactText.error = "Please, Enter your contact number"
            }else{
                userViewModel.register(email, password){
                        success, message, userId ->
                    if (success){
                        var userModel = UserModel(
                            userId.toString(),userType,fullName, email, Contact
                        )
                        userViewModel.addUserToDatabase(userId, userModel){
                                success, message ->
                            if(success){
                                Toast.makeText(this@RegistrationActivity,
                                    message,Toast.LENGTH_LONG).show()
                                val intent = Intent(this@RegistrationActivity, LoginPage::class.java)
                                startActivity(intent)
                                finish() // Finish RegistrationActivity so it doesn't stay in back stack

                            }else{
                                Toast.makeText(this@RegistrationActivity,
                                    message,Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        Toast.makeText(this@RegistrationActivity,
                            message,Toast.LENGTH_LONG).show()
                    }
                }


            }





//            auth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener {
//                    if(it.isSuccessful){
//                        var userId = auth.currentUser?.uid
//
//                        var userModel = UserModel(
//                            userId.toString(), userType, fullName, email, Contact
//                        )
//
//                        ref.child(userId.toString()).setValue(userModel).addOnCompleteListener {
//                            if(it.isSuccessful){
//                                Toast.makeText(this@RegistrationActivity, "Registration Success",
//                                    Toast.LENGTH_LONG).show()
//                            }else{
//                                Toast.makeText(this@RegistrationActivity, it.exception?.message.toString(),
//                                    Toast.LENGTH_LONG).show()
//                            }
//                        }
//
//                        Toast.makeText(this@RegistrationActivity, "Registration Success",
//                            Toast.LENGTH_LONG).show()
//
//                       if(it.isSuccessful) {
//                           finish()
//                       }
//                    }else{
//                        Toast.makeText(this@RegistrationActivity, it.exception?.message.toString(),
//                            Toast.LENGTH_LONG).show()
//                    }
//                }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}