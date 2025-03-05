package com.example.karyaconnectnepal.Repository

import android.content.Context
import android.net.Uri
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommonRepository {

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dtegcviek",
            "api_key" to "822158952482675",
            "api_secret" to "xGZ0_esftpw13G-9xhbgOA4wjeU"
        )
    )

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference.child("users")

    suspend fun uploadProfileImage(context: Context, imageUri: Uri, callback: (Boolean, String) -> Unit) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            callback(false, "User not logged in")
            return
        }

        try {
            withContext(Dispatchers.IO) { // Run network operations on background thread
                val inputStream = context.contentResolver.openInputStream(imageUri)
                    ?: throw Exception("Failed to open image")

                val response = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap())
                val imageUrl = response["secure_url"] as? String ?: ""

                if (imageUrl.isNotEmpty()) {
                    database.child(currentUser.uid).child("profileImage").setValue(imageUrl)
                        .addOnSuccessListener {
                            callback(true, imageUrl)
                        }
                        .addOnFailureListener { e ->
                            callback(false, "Failed to store image URL: ${e.message}")
                        }
                } else {
                    callback(false, "Cloudinary upload failed")
                }
            }
        } catch (e: Exception) {
            callback(false, "Upload failed: ${e.message}")
        }
    }
}



