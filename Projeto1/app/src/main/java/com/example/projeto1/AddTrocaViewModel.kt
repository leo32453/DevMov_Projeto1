package com.example.projeto1

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
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

class AddTrocaViewModel(
    application: Application,
    private val repository: TrocasRepository
) : AndroidViewModel(application) {

    private val savedLoginDao = AppDatabase.getDatabase(application).savedLoginDao()

    var bookName = mutableStateOf("")
        private set

    var bookState = mutableStateOf(getApplication<Application>().getString(R.string.state_new))
        private set

    var suggestions = mutableStateOf("")
        private set

    var onlySuggestions = mutableStateOf(true)
        private set

    internal val categories = listOf(
        getApplication<Application>().getString(R.string.category_fantasy),
        getApplication<Application>().getString(R.string.category_scifi),
        getApplication<Application>().getString(R.string.category_mystery),
        getApplication<Application>().getString(R.string.category_romance),
        getApplication<Application>().getString(R.string.category_horror),
        getApplication<Application>().getString(R.string.category_nonfiction)
    )
    val selectedCategories = mutableStateMapOf<String, Boolean>().apply {
        categories.forEach { put(it, false) }
    }

    val bookStates = listOf(
        getApplication<Application>().getString(R.string.state_new),
        getApplication<Application>().getString(R.string.state_used),
        getApplication<Application>().getString(R.string.state_good),
        getApplication<Application>().getString(R.string.state_damaged)
    )

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    private val _uiStatusMessage = MutableStateFlow<String?>(null)
    val uiStatusMessage: StateFlow<String?> = _uiStatusMessage


    fun onBookNameChange(newName: String) {
        bookName.value = newName
    }

    fun onBookStateChange(newState: String) {
        bookState.value = newState
    }

    fun onSuggestionsChange(newText: String) {
        suggestions.value = newText
    }

    fun onOnlySuggestionsToggle(value: Boolean) {
        onlySuggestions.value = value
    }

    fun toggleCategory(category: String) {
        selectedCategories[category] = !(selectedCategories[category] ?: false)
    }

    fun adicionarTroca() {
        viewModelScope.launch {
            if (bookName.value.isBlank()) {
                _eventFlow.emit(getApplication<Application>().getString(R.string.validation_book_name_required))
                _uiStatusMessage.value = getApplication<Application>().getString(R.string.validation_book_name_required)
                return@launch
            }

            if (bookState.value.isBlank()) {
                _eventFlow.emit(getApplication<Application>().getString(R.string.validation_book_state_required))
                _uiStatusMessage.value = getApplication<Application>().getString(R.string.validation_book_state_required)
                return@launch
            }

            try {
                val trocas = repository.getTrocas()
                val newId = (trocas.maxOfOrNull { it.exchange_id } ?: 0) + 1

                val userId = savedLoginDao.getUserId()
                if (userId == null) {
                    _eventFlow.emit(getApplication<Application>().getString(R.string.error_user_not_found_add_troca))
                    _uiStatusMessage.value = getApplication<Application>().getString(R.string.error_user_not_found_add_troca)
                    return@launch
                }

                val searchingFor = if (onlySuggestions.value) {
                    ""
                } else {
                    selectedCategories.filter { it.value }.keys.joinToString(", ")
                }

                val novaTroca = ExchangeData(
                    id = null,
                    exchange_id = newId,
                    solicitor_id = userId.toInt(),
                    book_name = bookName.value,
                    book_state = bookState.value,
                    searching_for = searchingFor,
                    sugested = suggestions.value,
                    offerings = emptyList()
                )

                repository.postTroca(novaTroca)
                _eventFlow.emit(getApplication<Application>().getString(R.string.success_add_trade))
                _uiStatusMessage.value = getApplication<Application>().getString(R.string.success_add_trade)

                bookName.value = ""
                bookState.value = getApplication<Application>().getString(R.string.state_new)
                suggestions.value = ""
                onlySuggestions.value = true
                categories.forEach { selectedCategories[it] = false }
            } catch (e: Exception) {
                e.printStackTrace()
                _eventFlow.emit(getApplication<Application>().getString(R.string.add_troca_error_status, e.localizedMessage))
                _uiStatusMessage.value = getApplication<Application>().getString(R.string.add_troca_error_status, e.localizedMessage)
            }
        }
    }
}