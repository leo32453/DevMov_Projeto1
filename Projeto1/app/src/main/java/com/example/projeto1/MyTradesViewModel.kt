package com.example.projeto1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.room.SavedLogin
import kotlinx.coroutines.launch

class MyTradesViewModel(
    val repository: TrocasRepository = TrocasRepository(),
    val savedLoginRepository: SavedLoginRepository
) : ViewModel() {
    
    var trocas by mutableStateOf<List<ExchangeData>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        fetchTrocas()
    }

    private fun fetchTrocas() {
        isLoading = true
        viewModelScope.launch {
            try {
                val saved = savedLoginRepository.getAll2()
                trocas = repository.getMinhasTrocas(saved.id.toString())
                Log.i("MyTradesViewModel", "Loading trades from id ${saved.id}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
