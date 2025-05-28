package com.example.projeto1.ui.viewmodel

import MinhasTrocasViewModel
import TrocasViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository

class MinhasTrocasViewModelFactory(
    private val application: Application,
    private val repository: TrocasRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MinhasTrocasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MinhasTrocasViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
