package com.example.karyaconnectnepal.Repository

import com.example.karyaconnectnepal.Model.PortfolioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class PortfolioRepository {

    private val database = FirebaseDatabase.getInstance().reference.child("users")
    private val auth = FirebaseAuth.getInstance()

    suspend fun savePortfolioData(userId: String, portfolio: PortfolioModel): Boolean {
        return try {
            val updates = mapOf(
                "portfolioData/personalInfo" to portfolio.personalInfo,  // ✅ Stores personal info separately
                "portfolioData/education" to portfolio.education,
                "portfolioData/skills" to portfolio.skills,
                "portfolioData/certifications" to portfolio.certifications,
                "portfolioData/experience" to portfolio.experience,
                "portfolioData/portfolioDescription" to portfolio.portfolioDescription
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
}
