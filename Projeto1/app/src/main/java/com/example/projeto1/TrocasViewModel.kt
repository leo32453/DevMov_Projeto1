import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import kotlinx.coroutines.launch

class TrocasViewModel(
    private val repository: TrocasRepository = TrocasRepository()
) : ViewModel() {
    
    var trocas by mutableStateOf<List<ExchangeData>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        fetchTrocas()
    }

    private fun fetchTrocas() {
        viewModelScope.launch {
            try {
                trocas = repository.getTrocas()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
