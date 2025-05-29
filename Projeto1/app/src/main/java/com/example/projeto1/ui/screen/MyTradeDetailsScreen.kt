package com.example.projeto1.ui.screen

import android.app.Application
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto1.MyTradeDetailsViewModel
import com.example.projeto1.R
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.ui.components.OfferCard
import com.example.projeto1.ui.viewmodel.MyTradeDetailsViewModelFactory

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

    /*
    viewModel.trocas : List<ExchangeData>
    troca : ExchangeData
    encontra troca
    */
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
                            Icons.AutoMirrored.Filled.ArrowBack,
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
                }
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                // Dados do livro
                Column {
                    if (troca != null) {
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

            Spacer(modifier = Modifier.height(24.dp))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = stringResource(R.string.offers),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
            // Dados das ofertas
            Column {
                val offers = troca?.offerings
                if (offers.isNullOrEmpty()) {
                    Text(stringResource(R.string.no_offers_found))
                } else {
                    /* Cria card para cada oferta encontrada
                    onClick = { } não faz nada nessa versão,
                    pode ser implementado em uma versão futura, */
                    offers.forEach { offered_trade ->
                        OfferCard(offered_trade = offered_trade, onClick = { })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }
    }
}