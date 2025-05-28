package com.example.projeto1.ui.viewmodel

import TrocasViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository

class TrocasViewModelFactory(
    private val application: Application,
    private val repository: TrocasRepository,
    private val savedLoginRepository: SavedLoginRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrocasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrocasViewModel(application, repository, savedLoginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
