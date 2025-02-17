package com.example.karyaconnectnepal.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyaconnectnepal.Model.PortfolioModel
import com.example.karyaconnectnepal.Repository.PortfolioRepository
import kotlinx.coroutines.launch

class PortfolioViewModel() : ViewModel() {

    private val repository = PortfolioRepository()  // ✅ Initialize repository inside ViewModel


    private val _portfolio = MutableLiveData<PortfolioModel?>()
    val portfolio: LiveData<PortfolioModel?> get() = _portfolio

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
}
