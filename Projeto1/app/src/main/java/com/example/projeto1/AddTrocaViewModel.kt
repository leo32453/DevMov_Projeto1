package com.example.projeto1

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddTrocaViewModel(
    application: Application,
    private val repository: TrocasRepository
) : AndroidViewModel(application) {

    private val savedLoginDao = AppDatabase.getDatabase(application).savedLoginDao()

    // Estado do formulário
    var bookName = mutableStateOf("")
        private set

    var bookState = mutableStateOf("Novo")
        private set

    var suggestions = mutableStateOf("")
        private set

    var onlySuggestions = mutableStateOf(true)
        private set

    private val categories = listOf("Fantasia", "Ficção científica", "Mistério", "Romance", "Terror", "Não ficção")
    val selectedCategories = mutableStateMapOf<String, Boolean>().apply {
        categories.forEach { put(it, false) }
    }

    // Status do envio
    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status

    // Manipuladores
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
        // Validação dos campos obrigatórios
        if (bookName.value.isBlank()) {
            _status.value = "O nome do livro é obrigatório."
            return
        }

        if (bookState.value.isBlank()) {
            _status.value = "O estado do livro é obrigatório."
            return
        }


        viewModelScope.launch {
            try {
                val trocas = repository.getTrocas()
                val newId = (trocas.maxOfOrNull { it.exchange_id } ?: 0) + 1
                val userId = savedLoginDao.getUserId() ?: run {
                    _status.value = "Erro: Usuário não encontrado."
                    return@launch
                }

                val searchingFor = if (onlySuggestions.value) {
                    ""
                } else {
                    selectedCategories.filter { it.value }.keys.joinToString(", ")
                }

                val novaTroca = ExchangeData(
                    exchange_id = newId,
                    solicitor_id = userId.toInt(),
                    book_name = bookName.value,
                    book_state = bookState.value,
                    searching_for = searchingFor,
                    sugested = suggestions.value,
                    offerings = emptyList()
                )

                repository.postTroca(novaTroca)
                _status.value = "Sucesso"
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = "Erro: ${e.localizedMessage}"
            }
        }
    }
}


