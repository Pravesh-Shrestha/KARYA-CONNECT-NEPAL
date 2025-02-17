package com.example.karyaconnectnepal.Repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.cloudinary.Cloudinary
import com.example.karyaconnectnepal.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepositoryImplementation : userRepository {

     var auth: FirebaseAuth = FirebaseAuth.getInstance()

    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref : DatabaseReference = database.reference.child("users")

//    var currentUserType: String? = null // Stores userType locally after login

    override fun login(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    getUserTypeFromDatabase(userId) { userType, success, message ->
                        if (success) {
                            callback(true, "Login Success",userType?.userType.toString())
                        } else {
                            callback(false, message,"")
                        }
                    }
                } else {
                    callback(false, "", "User ID not found")
                }
            } else {
                callback(false, "", it.exception?.message.toString())
            }
        }
//        auth.signInWithEmailAndPassword(email, password). addOnCompleteListener {
//            if(it.isSuccessful){
//                callback(true,"Login Success")
//            }else{
//                callback(false,it.exception?.message.toString())
//            }
//        }
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password). addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Registration Success", auth.currentUser?.uid.toString())
            }else{
                callback(false,it.exception?.message.toString(),"")
            }
        }
    }

    override fun addUserToDatabase(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {

        ref.child(userId).setValue(userModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Registration Success")
            }else{
                callback(false, it.exception?.message.toString())

            }
        }

    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            callback(true, "Reset mail sent to e-mail")
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getUserFromDatabase(
        userId: String,
        callback: (UserModel?, Boolean, String) -> Unit
    ) {
        ref.child(userId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val model = snapshot.getValue(UserModel::class.java)
                    if(model != null) {
                        callback(model, true, "User fetched successfully")
                    }else{
                        callback(null, false, "model is null")
                    }
                }else {
                    callback(null, false, "User not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
               callback(null,false,"error")
            }
        })
    }



    override fun getUserTypeFromDatabase(userId: String, callback: (UserModel?, Boolean, String) -> Unit) {
        ref.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val model = snapshot.getValue(UserModel ::class.java)
                    callback(model,true,"")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        TODO("Not yet implemented")
    }

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dtegcviek",
            "api_key" to "636548218447953",
            "api_secret" to "N2vXM2WRadH9tA8IQF_i1P2i_m0"
        )
    )
}

