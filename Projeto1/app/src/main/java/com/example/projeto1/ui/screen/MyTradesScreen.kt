package com.example.projeto1.ui.screen

import MyTradesViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto1.R
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.ui.components.TrocaCard
import com.example.projeto1.ui.viewmodel.MyTradesViewModelFactory
import androidx.compose.material.icons.automirrored.filled.Logout
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTradesScreen(
    trocasRepository: TrocasRepository,
    savedLoginRepository: SavedLoginRepository,
    onLogout: () -> Unit = {},
    onTrocaClick: (Int) -> Unit
) {
    val viewModel: MyTradesViewModel = viewModel(
        factory = MyTradesViewModelFactory(trocasRepository, savedLoginRepository)
    )

    val coroutineScope = rememberCoroutineScope()
    // Chama o logout pro botão
    val logout_aux: () -> Unit = {
        coroutineScope.launch {
            viewModel.logout()
            onLogout()
        }
    }

    val trocas = viewModel.trocas
    val isLoading = viewModel.isLoading

    var searchQuery by remember { mutableStateOf("") }
    var filteredTrocas by remember { mutableStateOf(trocas) }

    /*
        Filtra pela barra de pesquisa
     */
    LaunchedEffect(trocas, searchQuery) {
        filteredTrocas = if (searchQuery.isBlank()) {
            trocas
        } else {
            trocas.filter { troca ->
                troca.book_name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_trades)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                // Botão de logout
                actions = {
                    IconButton(onClick = logout_aux) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = stringResource(R.string.logout)
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                filteredTrocas.forEach { troca ->
                    TrocaCard(troca = troca, onClick = { onTrocaClick(troca.exchange_id) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
