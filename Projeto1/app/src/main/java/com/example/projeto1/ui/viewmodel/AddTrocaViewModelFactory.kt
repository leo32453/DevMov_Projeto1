package com.example.projeto1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.AddTrocaViewModel
import com.example.projeto1.repository.TrocasRepository

class AddTrocaViewModelFactory(
    private val application: Application,
    private val repository: TrocasRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTrocaViewModel::class.java)) {
            return AddTrocaViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}