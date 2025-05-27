import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto1.components.TrocaCard
import com.example.projeto1.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TrocasViewModel = viewModel(),
    onLogout: () -> Unit = {}
) {
    val trocas = viewModel.trocas
    val isLoading = viewModel.isLoading

    var searchQuery by remember { mutableStateOf("") }
    var filteredTrocas by remember { mutableStateOf(trocas) }

    // Atualiza filteredTrocas sempre que trocas ou searchQuery mudarem
    LaunchedEffect(trocas, searchQuery) {
        filteredTrocas = if (searchQuery.isBlank()) {
            trocas
        } else {
            trocas.filter { troca ->
                troca.book_name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.explore_trades)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(stringResource(id = R.string.search_label)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50.dp),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                filteredTrocas.forEach { troca ->
                    TrocaCard(troca = troca)
                    Spacer(modifier = Modifier.padding(0.dp))
                }
            }
        }
    }
}
