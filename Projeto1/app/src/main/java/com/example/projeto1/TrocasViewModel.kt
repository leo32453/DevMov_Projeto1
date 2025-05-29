import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.room.AppDatabase
import kotlinx.coroutines.launch

class TrocasViewModel(
    application: Application,
    private val repository: TrocasRepository,
    private val savedLoginRepository: SavedLoginRepository,
) : AndroidViewModel(application) {

    private val savedLoginDao by lazy {
        AppDatabase.getDatabase(application).savedLoginDao()
    }

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
                val todasTrocas = repository.getTrocas()
                val userId = savedLoginDao.getUserId()

                // Filtra trocas para não mostrar as que o próprio usuário solicitou
                trocas = if (userId != null) {
                    todasTrocas.filter { it.solicitor_id.toLong() != userId }
                } else {
                    todasTrocas
                }
            } catch (e: Exception) {
                trocas = emptyList()
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    suspend fun logout(){
        Log.i("MainViewModel", "Logout Clicked")
        savedLoginRepository.deleteAll()
    }
}