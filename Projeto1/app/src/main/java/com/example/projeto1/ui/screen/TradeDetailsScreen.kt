package com.example.projeto1.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto1.R
import com.example.projeto1.TradeDetailsViewModel
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.ui.viewmodel.TradeDetailsViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeDetailsScreen(
    exchangeId: Int,
    repository: TrocasRepository,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val viewModel: TradeDetailsViewModel = viewModel(
        factory = TradeDetailsViewModelFactory(application, repository, exchangeId)
    )

    val trocaState = viewModel.troca.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val offerBookName = viewModel.offerBookName.collectAsState()
    val offerBookState = viewModel.offerBookState.collectAsState()
    val bookStates = viewModel.bookStates

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.trade_detail)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.voltar)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(30.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                when {
                    isLoading.value -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    errorMessage.value != null -> {
                        Text(
                            text = errorMessage.value ?: stringResource(R.string.erro_carregamento),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    trocaState.value != null -> {
                        val troca = trocaState.value!!
                        Column {
                            Text(
                                text = stringResource(R.string.livro, troca.book_name),
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.estado, troca.book_state),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.buscando, troca.searching_for),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.sugestoes, troca.sugested),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.sua_oferta),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = offerBookName.value,
                        onValueChange = { viewModel.updateOfferBookName(it) },
                        label = { Text(stringResource(R.string.nome_livro)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.estado_livro),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(Modifier.selectableGroup()) {
                        bookStates.forEach { state ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .selectable(
                                        selected = (offerBookState.value == state),
                                        onClick = { viewModel.updateOfferBookState(state) },
                                        role = Role.RadioButton
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (offerBookState.value == state),
                                    onClick = null
                                )
                                Text(
                                    text = state,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.submitOffer() },
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.solicitar))
                    }
                }
            }
        }
    }
}