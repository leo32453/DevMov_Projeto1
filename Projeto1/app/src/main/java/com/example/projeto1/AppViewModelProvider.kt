package com.example.projeto1

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for LoginViewModel
        initializer {
            LoginViewModel(appApplication().container.savedLoginRepository)
        }

    }
}

fun CreationExtras.appApplication(): AppApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppApplication)