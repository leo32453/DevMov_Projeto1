package com.example.projeto1.ui.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto1.MyTradeDetailsViewModel
import com.example.projeto1.R
import com.example.projeto1.TradeDetailsViewModel
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.ui.components.OfferCard
import com.example.projeto1.ui.components.TrocaCard
import com.example.projeto1.ui.viewmodel.MyTradeDetailsViewModelFactory
import com.example.projeto1.ui.viewmodel.TradeDetailsViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTradeDetailsScreen(
    exchangeId: Int,
    trocasRepository: TrocasRepository,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val viewModel: MyTradeDetailsViewModel = viewModel(
        factory = MyTradeDetailsViewModelFactory(application, trocasRepository, exchangeId)
    )

    // troca com o id correto
    val trocas = viewModel.trocas
    var troca = trocas.find { troca -> (troca.exchange_id == viewModel.id) }

    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

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
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(30.dp)
                    .fillMaxWidth()
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: stringResource(R.string.erro_carregamento),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Column {
                    if (troca != null) {
                        Text(
                            text = stringResource(R.string.livro, troca.book_name),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (troca != null) {
                        Text(
                            text = stringResource(R.string.estado, troca.book_state),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (troca != null) {
                        Text(
                            text = stringResource(R.string.buscando, troca.searching_for),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (troca != null) {
                        Text(
                            text = stringResource(R.string.sugestoes, troca.sugested),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.offers),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Column {
                val offers = troca?.offerings
                if (offers != null) {
                    offers.forEach { offered_trade ->
                        OfferCard(offered_trade = offered_trade, onClick = { })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    Text(stringResource(R.string.no_offers_found))
                }
            }

        }
    }
}