package com.example.projeto1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.room.AppDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TradeDetailsViewModel(
    application: Application,
    private val repository: TrocasRepository,
    private val exchangeId: Int
) : AndroidViewModel(application) {

    private val savedLoginDao = AppDatabase.getDatabase(application).savedLoginDao()

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    private val _troca = MutableStateFlow<ExchangeData?>(null)
    val troca: StateFlow<ExchangeData?> = _troca

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _offerBookName = MutableStateFlow("")
    val offerBookName: StateFlow<String> = _offerBookName

    val bookStates = listOf(
        getApplication<Application>().getString(R.string.state_new),
        getApplication<Application>().getString(R.string.state_used),
        getApplication<Application>().getString(R.string.state_good),
        getApplication<Application>().getString(R.string.state_damaged)
    )
    private val _offerBookState = MutableStateFlow(bookStates.first())
    val offerBookState: StateFlow<String> = _offerBookState

    init {
        loadTroca()
    }

    private fun loadTroca() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val trocas = repository.getTrocas()
                _troca.value = trocas.find { it.exchange_id == exchangeId }
                if (_troca.value == null) {
                    _errorMessage.value = getApplication<Application>().getString(R.string.error_trade_not_found)
                } else {
                    _errorMessage.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = getApplication<Application>().getString(R.string.error_loading_details, e.localizedMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateOfferBookName(name: String) {
        _offerBookName.value = name
    }

    fun updateOfferBookState(state: String) {
        _offerBookState.value = state
    }

    fun submitOffer() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentTroca = _troca.value ?: run {
                    _eventFlow.emit(getApplication<Application>().getString(R.string.error_trade_details_not_loaded))
                    _isLoading.value = false
                    return@launch
                }

                if (_offerBookName.value.isBlank()) {
                    _eventFlow.emit(getApplication<Application>().getString(R.string.error_offer_book_name_required))
                    _isLoading.value = false
                    return@launch
                }

                val userId = savedLoginDao.getUserId()?.toInt() ?: run {
                    _eventFlow.emit(getApplication<Application>().getString(R.string.error_user_not_found))
                    _isLoading.value = false
                    return@launch
                }

                repository.addOfferToExchange(
                    exchangeId = currentTroca.exchange_id,
                    userId = userId,
                    bookName = _offerBookName.value,
                    bookState = _offerBookState.value
                )

                _eventFlow.emit(getApplication<Application>().getString(R.string.offer_sent_successfully))
                _offerBookName.value = ""
                _offerBookState.value = bookStates.first()
            } catch (e: Exception) {
                _eventFlow.emit(getApplication<Application>().getString(R.string.error_sending_offer, e.localizedMessage))
            } finally {
                _isLoading.value = false
            }
        }
    }
}