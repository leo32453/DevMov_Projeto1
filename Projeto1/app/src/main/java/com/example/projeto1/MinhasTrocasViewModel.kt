import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.room.AppDatabase
import kotlinx.coroutines.launch

class MinhasTrocasViewModel(
    application: Application,
    private val repository: TrocasRepository
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
        isLoading = true
        viewModelScope.launch {
            try {
                val saved = savedLoginDao.getUserId()
                trocas = repository.getMinhasTrocas(saved.toString())
                Log.i("MyTradesViewModel", "Loading trades from id ${saved}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}