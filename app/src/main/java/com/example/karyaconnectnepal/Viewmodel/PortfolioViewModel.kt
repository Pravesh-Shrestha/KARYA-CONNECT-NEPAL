package com.example.karyaconnectnepal.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.Model.PortfolioModel
import com.example.karyaconnectnepal.Repository.PortfolioRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class PortfolioViewModel() : ViewModel() {

    private val repository = PortfolioRepository()  // ✅ Initialize repository inside ViewModel


    private val _portfolio = MutableLiveData<PortfolioModel?>()
    val portfolio: LiveData<PortfolioModel?> get() = _portfolio

    private val _freelancerPortfolios = MutableLiveData<List<PortfolioDisplayModel>>()
    val freelancerPortfolios: LiveData<List<PortfolioDisplayModel>> get() = _freelancerPortfolios

//    val freelancerPortfolios = MutableLiveData<List<PortfolioDisplayModel>>()

    fun savePortfolio(userId: String, portfolio: PortfolioModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.savePortfolioData(userId, portfolio)
            callback(success)
        }
    }

    fun loadPortfolio(userId: String) {
        viewModelScope.launch {
            _portfolio.value = repository.getPortfolioData(userId)
        }
    }

    fun loadFreelancerPortfolio(userId: String) {
        viewModelScope.launch {
            val portfolioData = repository.getFreelancerPortfolio(userId)
            if (portfolioData == null) {
                Log.e("PortfolioViewModel", "No data found for userId: $userId")
            } else {
                Log.d("PortfolioViewModel", "Portfolio fetched successfully: $portfolioData")
            }
            _portfolio.value = portfolioData
        }
    }
    fun getAllFreelancerPortfolios() {
        val portfolioRef = FirebaseDatabase.getInstance().reference.child("users")

        portfolioRef.get().addOnSuccessListener { snapshot ->
            val freelancers = mutableListOf<PortfolioDisplayModel>()

            if (!snapshot.exists()) {
                Log.e("FIREBASE_DEBUG", "No users found in Firebase!")
                return@addOnSuccessListener
            }

            for (userSnapshot in snapshot.children) {
                val userId = userSnapshot.key ?: continue
                val userType = userSnapshot.child("userType").value as? String ?: "client"  // ✅ Check userType

                Log.d("FIREBASE_DEBUG", "User ID: $userId, UserType: $userType")

                if (userType == "Freelancer") {  // ✅ Fetch only freelancers
                    val fullName = userSnapshot.child("fullName").value as? String ?: "N/A"
                    val jobCategory = userSnapshot.child("portfolioData").child("jobCategory").value as? String ?: "Not Specified"
                    val profileImage = userSnapshot.child("profileImage").value as? String ?: ""

                    Log.d("FIREBASE_DEBUG", "Freelancer Found: $fullName ($jobCategory)")

                    freelancers.add(PortfolioDisplayModel(userId, fullName, jobCategory, profileImage))
                }
            }

            if (freelancers.isEmpty()) {
                Log.e("FIREBASE_DEBUG", "No freelancers found!")
            } else {
                Log.d("FIREBASE_DEBUG", "Total Freelancers: ${freelancers.size}")
            }

            _freelancerPortfolios.value = freelancers  // ✅ Update the UI
        }.addOnFailureListener { exception ->
            Log.e("FIREBASE_DEBUG", "Error fetching freelancers: ${exception.message}")
        }
    }

}
