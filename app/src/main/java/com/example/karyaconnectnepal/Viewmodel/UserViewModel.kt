package com.example.karyaconnectnepal.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.karyaconnectnepal.Model.UserModel
import androidx.lifecycle.viewModelScope
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.example.karyaconnectnepal.Repository.userRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch  // Import for launching coroutines


class UserViewModel(var repo : UserRepositoryImplementation) {



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

    // LiveData for general user data
    var _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?>
        get() = _user
//  var user= MutableLiveData<UserModel?>()
////        get() = _user

    // Fetch full user data from the database
    fun getUserFromDatabase(userId: String) {

        repo.getUserFromDatabase(userId) { user, success, message ->
            if (success) {
                _user.value = user
            }
        }
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

    // ✅ New: LiveData for Portfolio Data
    private var _portfolioData = MutableLiveData<Map<String, Any>?>()
    val portfolioData: LiveData<Map<String, Any>?> get() = _portfolioData

//    // ✅ Method to Save Portfolio Data
//    fun savePortfolioData(
//        userId: String,
//        personalInfo: Map<String, Any>,
//        portfolioData: Map<String, Any>,
//        callback: (Boolean) -> Unit
//    ) {
//        viewModelScope.launch {
//            val success = repo.savePortfolioData(userId, personalInfo, portfolioData)
//            callback(success)
//        }
//    }
//
//    // ✅ Method to Fetch Portfolio Data
//    fun getPortfolioData(userId: String) {
//        viewModelScope.launch {
//            val userData = repo.getUserProfile(userId)
//            _portfolioData.value = userData?.portfolioData
//        }
//    }

}

