package com.example.projeto1.ui.viewmodel

import MyTradesViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository

/*
    Factory cria viewmodel com parametros
 */
class MyTradesViewModelFactory(
    private val repository: TrocasRepository,
    private val savedLoginRepository: SavedLoginRepository

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTradesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyTradesViewModel(repository, savedLoginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
