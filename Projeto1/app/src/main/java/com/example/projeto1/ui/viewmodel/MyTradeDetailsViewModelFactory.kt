package com.example.projeto1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.MyTradeDetailsViewModel
import com.example.projeto1.repository.TrocasRepository

class MyTradeDetailsViewModelFactory(
    private val application: Application,
    private val repository: TrocasRepository,
    private val exchangeId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTradeDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyTradeDetailsViewModel(application, repository, exchangeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}