package com.example.karyaconnectnepal.Model

data class PortfolioModel(
    val personalInfo: Map<String, Any> = emptyMap(), // Separate from registration details
    val education: String = "",
    val skills: List<String> = emptyList(),
    val certifications: List<String> = emptyList(),
    val experience: String = "",
    val portfolioDescription: String = "",
    val jobCategory: String = ""
)
