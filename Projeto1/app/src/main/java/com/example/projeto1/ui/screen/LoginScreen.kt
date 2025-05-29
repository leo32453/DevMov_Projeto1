package com.example.projeto1.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projeto1.ui.theme.Projeto1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projeto1.AppViewModelProvider
import com.example.projeto1.LoginViewModel
import com.example.projeto1.R
import com.example.projeto1.navigation.Destination

@Composable
fun LoginScreenWithNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Implementação do corpo da função
    LoginScreen(
        modifier = modifier,
        viewModel = viewModel,
        onSuccessfulLogin = {
            // Navega para a tela principal após login bem-sucedido
            navController.navigate(Destination.Explore.route) {
                // Limpa a pilha de navegação
                popUpTo("login") { inclusive = true }
            }
        },
        navigateUp = {
            // Volta para a tela anterior
            navController.popBackStack()
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// viewModel provides functions
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onSuccessfulLogin : () -> Unit = {},
    navigateUp : () -> Unit = {},)
{
    LaunchedEffect(Unit){
        viewModel.useSavedLogin()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_title)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column (
            modifier = modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        )
        {
            Column(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(vertical = 30.dp, horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = stringResource(R.string.login_label)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                // Username Field
                OutlinedTextField(
                    value = viewModel.username,
                    isError = viewModel.usernameError,
                    supportingText = {
                        if (viewModel.usernameError) {
                            Text(
                                text = stringResource(viewModel.usernameErrorID),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.username = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.username_label)) }
                )
                Spacer(modifier = Modifier.padding(10.dp))

                // Password Field
                OutlinedTextField(
                    value = viewModel.password,
                    isError = viewModel.passwordError,
                    supportingText = {
                        if (viewModel.passwordError) {
                            Text(
                                text = stringResource(viewModel.passwordErrorID),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { viewModel.password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.password_label)) },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.padding(10.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = viewModel::clearLogin) {
                        Text(stringResource(R.string.clear_login_label))
                    }
                    Button(onClick = viewModel::performLogin) {
                        Text(stringResource(R.string.confirm_login_label))
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedButton(onClick = viewModel::createAccount) {
                    Text(stringResource(R.string.create_account_label))
                }
            }
        }
        if (viewModel.isLoginSuccessful) {
            onSuccessfulLogin()
            Log.i("LoginScreen", "onSuccessfulLogin()")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BodyPreview() {
    Projeto1Theme {
        LoginScreen()
    }
}