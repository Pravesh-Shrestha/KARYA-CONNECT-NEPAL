package com.example.karyaconnectnepal.Repository

import android.util.Log
import com.example.karyaconnectnepal.Model.PortfolioDisplayModel
import com.example.karyaconnectnepal.Model.PortfolioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

class PortfolioRepository {

    private val database = FirebaseDatabase.getInstance().reference.child("users")
    private val auth = FirebaseAuth.getInstance()

    suspend fun savePortfolioData(userId: String, portfolio: PortfolioModel): Boolean {
        return try {
            val updates = mapOf(
                "portfolioData/personalInfo" to portfolio.personalInfo,  // Stores personal info separately
                "portfolioData/education" to portfolio.education,
                "portfolioData/skills" to portfolio.skills,
                "portfolioData/certifications" to portfolio.certifications,
                "portfolioData/experience" to portfolio.experience,
                "portfolioData/portfolioDescription" to portfolio.portfolioDescription,
                "portfolioData/jobCategory" to portfolio.jobCategory
            )
            database.child(userId).updateChildren(updates).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getPortfolioData(userId: String): PortfolioModel? {
        return try {
            val snapshot = database.child(userId).child("portfolioData").get().await()
            snapshot.getValue(PortfolioModel::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getFreelancerPortfolio(userId: String): PortfolioModel? {
        return try {
            val snapshot = database.child(userId).child("portfolioData").get().await()
            if (!snapshot.exists()) {
                Log.e("PortfolioRepository", "No data found for userId: $userId")
                return null
            }
            val portfolio = snapshot.getValue(PortfolioModel::class.java)
            Log.d("PortfolioRepository", "Portfolio fetched: $portfolio")
            portfolio
        } catch (e: Exception) {
            Log.e("PortfolioRepository", "Error fetching portfolio: ${e.message}")
            null
        }
    }


    fun fetchFreelancerPortfolios(callback: (List<PortfolioDisplayModel>) -> Unit) {
        database.orderByChild("userType").equalTo("Freelancer")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val freelancerList = mutableListOf<PortfolioDisplayModel>()
                    for (userSnapshot in snapshot.children) {
                        val userId = userSnapshot.key ?: continue
                        val fullName = userSnapshot.child("portfolioData/personalInfo/fullName").value as? String ?: "Unknown"
                        val jobCategory = userSnapshot.child("portfolioData/jobCategory").value as? String ?: "Not Specified"

                        freelancerList.add(PortfolioDisplayModel(userId, fullName, jobCategory))
                    }
                    callback(freelancerList)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }
}
