package com.example.projeto1.ui.screen

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto1.AddTrocaViewModel
import com.example.projeto1.R
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.ui.viewmodel.AddTrocaViewModelFactory
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTradeScreen(
    application: Application,
    onSuccess: () -> Unit = {},
    viewModel: AddTrocaViewModel = viewModel(
        factory = AddTrocaViewModelFactory(
            application,
            repository = TrocasRepository()
        )
    )
){
    val context = LocalContext.current
    val status by viewModel.status.collectAsState()

    val strings = LocalContext.current.resources

    LaunchedEffect(status) {
        if (status == "Sucesso") {
            Toast.makeText(context, context.getString(R.string.success_add_trade), Toast.LENGTH_SHORT).show()
            onSuccess()
        } else if (status?.startsWith("Erro") == true) {
            Toast.makeText(context, status, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title_add_trade)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.bookName.value,
                onValueChange = viewModel::onBookNameChange,
                label = { Text(stringResource(R.string.label_book_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(stringResource(R.string.label_book_state), style = MaterialTheme.typography.titleMedium)
            listOf(
                R.string.state_new,
                R.string.state_used,
                R.string.state_good,
                R.string.state_damaged
            ).forEach {
                val label = stringResource(id = it)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.bookState.value == label,
                        onClick = { viewModel.onBookStateChange(label) }
                    )
                    Text(label)
                }
            }

            Text(stringResource(R.string.label_searching), style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.onlySuggestions.value,
                    onCheckedChange = viewModel::onOnlySuggestionsToggle
                )
                Text(stringResource(R.string.only_suggestions))
            }

            if (!viewModel.onlySuggestions.value) {
                val categories = listOf(
                    R.string.category_fantasy,
                    R.string.category_scifi,
                    R.string.category_mystery,
                    R.string.category_romance,
                    R.string.category_horror,
                    R.string.category_nonfiction
                )

                categories.forEach {
                    val label = stringResource(id = it)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectedCategories[label] == true,
                            onCheckedChange = { viewModel.toggleCategory(label) }
                        )
                        Text(label)
                    }
                }
            }

            OutlinedTextField(
                value = viewModel.suggestions.value,
                onValueChange = viewModel::onSuggestionsChange,
                label = { Text(stringResource(R.string.label_suggestions)) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = viewModel::adicionarTroca,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.button_submit_trade))
            }
        }
    }
}

