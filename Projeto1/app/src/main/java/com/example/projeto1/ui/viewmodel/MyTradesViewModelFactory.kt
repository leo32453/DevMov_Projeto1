package com.example.projeto1.ui.viewmodel

import MyTradesViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository

class MyTradesViewModelFactory(
    private val application: Application,
    private val repository: TrocasRepository,
    private val savedLoginRepository: SavedLoginRepository

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTradesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyTradesViewModel(application, repository, savedLoginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
