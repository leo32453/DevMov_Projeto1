package com.example.projeto1

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.room.AppDatabase
import kotlinx.coroutines.launch

class MyTradeDetailsViewModel(
    application: Application,
    private val trocasRepository: TrocasRepository,
    private val exchangeId: Int
) : AndroidViewModel(application) {

    var isLoading = true
    var errorMessage = ""
    var trocas by mutableStateOf<List<ExchangeData>>(emptyList())
    val id = exchangeId

    init {
        loadTroca()
    }

    private fun loadTroca() {
        isLoading = true
        viewModelScope.launch {
            try {
                trocas = trocasRepository.getTrocaByExchangeId(exchangeId)
                Log.i("MyTradesDetailsViewModel", "Loading trades from exchange_id ${exchangeId}")
            } catch (e: Exception) {
                errorMessage = application.getString(R.string.error_loading_details)
            } finally {
                isLoading = false
            }
        }
    }

}