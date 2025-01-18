package com.example.karyaconnectnepal.Viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.karyaconnectnepal.Model.UserModel
import com.example.karyaconnectnepal.Repository.userRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(var repo : userRepository) {



    fun login(email:String,password:String,
              callback:(Boolean, String, String)-> Unit){
        repo.login(email, password, callback)
    }

    fun register(email:String,password:String,
                 callback:(Boolean, String, String)->Unit){
        repo.register(email, password, callback)
    }

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun forgetPassword(email: String,
                       callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email, callback)
    }

    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

    var _getUserTypeFromDatabase = MutableLiveData<UserModel?>()
    var getUserTypeFromDatabase = MutableLiveData<UserModel?>()
        get() = _getUserTypeFromDatabase

    fun getUserTypeFromDatabase(userId: String){
        repo.getUserTypeFromDatabase(userId){
            user,success,message->
            if(success){
                _getUserTypeFromDatabase.value = user
            }
        }
    }




}

