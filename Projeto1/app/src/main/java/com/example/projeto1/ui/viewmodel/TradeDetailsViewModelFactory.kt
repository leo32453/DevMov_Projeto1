package com.example.projeto1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.TradeDetailsViewModel
import com.example.projeto1.repository.TrocasRepository

class TradeDetailsViewModelFactory(
    private val application: Application,
    private val repository: TrocasRepository,
    private val exchangeId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TradeDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TradeDetailsViewModel(application, repository, exchangeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}