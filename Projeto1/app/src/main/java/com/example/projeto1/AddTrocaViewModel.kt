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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AddTrocaViewModel(
    application: Application,
    private val repository: TrocasRepository
) : AndroidViewModel(application) {

    private val savedLoginDao = AppDatabase.getDatabase(application).savedLoginDao()

    var bookName = mutableStateOf("")
        private set

    var bookState = mutableStateOf(getString(R.string.state_new))
        private set

    var suggestions = mutableStateOf("")
        private set

    var onlySuggestions = mutableStateOf(true)
        private set

    val categories: List<String> = listOf(
        getString(R.string.category_fantasy),
        getString(R.string.category_scifi),
        getString(R.string.category_mystery),
        getString(R.string.category_romance),
        getString(R.string.category_horror),
        getString(R.string.category_nonfiction)
    )
    val selectedCategories = mutableStateMapOf<String, Boolean>().apply {
        categories.forEach { put(it, false) }
    }

    val bookStates: List<String> = listOf(
        getString(R.string.state_new),
        getString(R.string.state_used),
        getString(R.string.state_good),
        getString(R.string.state_damaged)
    )

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow: SharedFlow<String> = _eventFlow

    private fun getString(resId: Int, vararg formatArgs: Any): String {
        return getApplication<Application>().getString(resId, *formatArgs)
    }

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
                _eventFlow.emit(getString(R.string.validation_book_name_required))
                return@launch
            }

            if (bookState.value.isBlank()) {
                _eventFlow.emit(getString(R.string.validation_book_state_required))
                return@launch
            }

            try {
                val userId = savedLoginDao.getUserId()?.toInt() ?: run {
                    _eventFlow.emit(getString(R.string.error_user_not_found_add_troca))
                    return@launch
                }

                val trocas = repository.getTrocas()
                val newId = (trocas.maxOfOrNull { it.exchange_id } ?: 0) + 1

                val searchingFor = if (onlySuggestions.value) {
                    getString(R.string.only_suggestions)
                } else {
                    selectedCategories.filter { it.value }.keys.joinToString(", ")
                }

                val novaTroca = ExchangeData(
                    id = null,
                    exchange_id = newId,
                    solicitor_id = userId,
                    book_name = bookName.value,
                    book_state = bookState.value,
                    searching_for = searchingFor,
                    sugested = suggestions.value,
                    offerings = emptyList()
                )

                repository.postTroca(novaTroca)
                _eventFlow.emit(getString(R.string.success_add_trade))

                bookName.value = ""
                bookState.value = getString(R.string.state_new)
                suggestions.value = ""
                onlySuggestions.value = true
                categories.forEach { selectedCategories[it] = false }
            } catch (e: Exception) {
                e.printStackTrace()
                _eventFlow.emit(getString(R.string.add_troca_error_status, e.localizedMessage))
            }
        }
    }
}