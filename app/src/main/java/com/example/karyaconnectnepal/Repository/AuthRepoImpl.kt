package com.example.karyaconnectnepal.Repository
import com.google.firebase.auth.FirebaseAuth
class AuthRepoImpl(private val auth: FirebaseAuth) : AuthRepo {

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login successful")
            } else {
                callback(false, it.exception?.message ?: "Login failed")
            }
        }
    }
    override fun register(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration successful")
            } else {
                callback(false, it.exception?.message ?: "Registration failed")
            }
        }
    }
    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Reset email sent successfully")
            } else {
                callback(false, it.exception?.message ?: "Failed to send reset email")
            }
        }
    }
}
