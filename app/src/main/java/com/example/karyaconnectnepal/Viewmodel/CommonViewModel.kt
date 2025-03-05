package com.example.karyaconnectnepal.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyaconnectnepal.Repository.CommonRepository
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.launch

class CommonViewModel : ViewModel() {
    private val repository = CommonRepository() // ✅ Initialize repository inside the ViewModel

    fun uploadProfileImage(context: Context, imageUri: Uri, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch { // ✅ Launch a coroutine to call suspend function
            repository.uploadProfileImage(context, imageUri, callback)
        }
    }
}

