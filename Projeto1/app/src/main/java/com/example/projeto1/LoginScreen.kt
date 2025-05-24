package com.example.projeto1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projeto1.ui.theme.Projeto1Theme
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// viewModel provides functions
fun LoginScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
//    var name by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }

//    val onLoginButtonClicked : () -> Unit = {
//        Log.i("LoginScreen", "name: $name, password: $password")
//    }
//
//    val onClearButtonClicked : () -> Unit = {
//        name = ""
//        password = ""
//    }
//
//    val onCreateAccountButtonClicked : () -> Unit = {
//        Log.i("LoginScreen", "Create Account Clicked")
//    }

    Column(
        modifier = modifier.fillMaxSize().padding(50.dp),
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
                        text = viewModel.usernameErrorMessage,
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
                        text = viewModel.passwordErrorMessage,
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
            Button(onClick = viewModel::performLogin) {
                Text(stringResource(R.string.clear_login_label))
            }
            Button(onClick = viewModel::clearLogin) {
                Text(stringResource(R.string.confirm_login_label))
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedButton(onClick = viewModel::createAccount) {
            Text(stringResource(R.string.create_account_label))
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