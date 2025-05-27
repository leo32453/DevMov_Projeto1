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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projeto1.ui.theme.Projeto1Theme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
// viewModel provides functions
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onSuccessfulLogin : () -> Unit = {},
    navigateUp : () -> Unit = {},)
{
    LaunchedEffect(Unit){
        viewModel.useSavedLogin()
    }
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