package com.example.karyaconnectnepal.Repository

interface AuthRepo {
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun register(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)
}
