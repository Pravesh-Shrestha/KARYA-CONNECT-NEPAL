package com.example.karyaconnectnepal.Repository

import com.example.karyaconnectnepal.Model.UserModel
import com.google.firebase.auth.FirebaseUser

interface userRepository {

//    {
//        "Success": true
//        "message": "Login Success"
//          "userId": "122q354645"
//    }


    fun login(email:String,password:String,
              callback:(Boolean, String,String)->Unit)

    fun register(email:String,password:String,
                 callback:(Boolean, String, String)->Unit)

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit)

    fun forgetPassword(email: String,
                       callback: (Boolean, String) -> Unit)

    fun getCurrentUser(): FirebaseUser?


    fun getUserFromDatabase(userId:String,callback: (UserModel?,Boolean, String) -> Unit)


    fun getUserTypeFromDatabase(userId: String, callback: (UserModel?, Boolean, String) -> Unit)

}