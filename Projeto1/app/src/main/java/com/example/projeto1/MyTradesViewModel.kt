import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.retrofit.ExchangeData
import kotlinx.coroutines.launch

class MyTradesViewModel(
    private val trocasRepository: TrocasRepository,
    val savedLoginRepository: SavedLoginRepository
) : ViewModel() {

    /*
        Cria lista de trocas vazia que será preenchida
     */
    var trocas by mutableStateOf<List<ExchangeData>>(emptyList())

    var isLoading = true

    init {
        fetchTrocas()
    }

    private fun fetchTrocas() {
        isLoading = true
        viewModelScope.launch {
            try {
                /*
                    Carrega id do usuário armazenado localmente
                    e as trocas relacionadas a ele
                 */
                val saved = savedLoginRepository.getUserId()
                trocas = trocasRepository.getMinhasTrocas(saved.toString())
                Log.i("MyTradesViewModel", "Loading trades from id ${saved}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    /*
        logout limpa o login salvo localmente
     */
    suspend fun logout(){
        Log.i("MyTradesViewModel", "Logout Clicked")
        savedLoginRepository.deleteAll()
    }
}