package com.example.karyaconnectnepal.Viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.karyaconnectnepal.Model.UserModel
import androidx.lifecycle.viewModelScope
import com.example.karyaconnectnepal.Repository.CommonRepository
import com.example.karyaconnectnepal.Repository.UserRepositoryImplementation
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class UserViewModel(private val repo: UserRepositoryImplementation) : ViewModel() {

    private val commonRepository = CommonRepository()



    fun login(email:String,password:String,
              callback:(Boolean, String, String)-> Unit){
        repo.login(email, password, callback)
    }

    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.register(email, password, callback)
    }

    fun addUserToDatabase(userId: String, userModel: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, userModel, callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    // LiveData for general user data
    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?>
        get() = _user

    // Fetch full user data from the database
    fun getUserFromDatabase(userId: String) {
        repo.getUserFromDatabase(userId) { user, success, message ->
            if (success) {
                _user.value = user
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUser()
    }

    private val _getUserTypeFromDatabase = MutableLiveData<UserModel?>()
    val getUserTypeFromDatabase: LiveData<UserModel?>
        get() = _getUserTypeFromDatabase

    fun getUserTypeFromDatabase(userId: String) {
        repo.getUserTypeFromDatabase(userId) { user, success, message ->
            if (success) {
                _getUserTypeFromDatabase.value = user
            }
        }
    }

    // LiveData for Portfolio Data
    private val _portfolioData = MutableLiveData<Map<String, Any>?>()
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

//    fun uploadProfileImage(context: Context, imageUri: Uri, callback: (Boolean, String) -> Unit) {
//        commonRepository.uploadProfileImage(context, imageUri, callback)
//    }


}

