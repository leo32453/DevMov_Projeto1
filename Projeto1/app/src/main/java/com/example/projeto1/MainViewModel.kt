package com.example.projeto1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var username by mutableStateOf("")
    var usernameError by mutableStateOf(false)
    var usernameErrorMessage by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordError by mutableStateOf(false)
    var passwordErrorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    val userRepository = UserRepository()

    fun performLogin() {
        usernameError = false
        passwordError = false

        username = username.trim()
        if (username == "") {
            usernameError = true
            usernameErrorMessage = R.string.error_empty_field.toString()
            return
        }

        if (password == "") {
            passwordError = true
            passwordErrorMessage = R.string.error_empty_field.toString()
            return
        }

        viewModelScope.launch {
            isLoading = true
            // userRepository handles username/password check, returns status
            val status = userRepository.login(username, password)
            isLoading = false
            Log.i("MainViewModel", "name: $username, password: $password, status: $status")
            when(status) {
                "success" -> {
                    Log.i("MainViewModel", "moving to main screen")
                }
                "wrong_username" -> {
                    usernameError = true
                    usernameErrorMessage = R.string.error_wrong_username.toString()
                }
                "wrong_password" -> {
                    passwordError = true
                    passwordErrorMessage = R.string.error_wrong_password.toString()
                }
                else -> {
                    usernameError = true
                    usernameErrorMessage = "${R.string.error.toString()}: $status"
                }
            }
        }
    }

    fun clearLogin() {
        username = ""
        password = ""
        usernameError = false
        passwordError = false
    }

    fun createAccount(){
        Log.i("MainViewModel", "Create Account Clicked")
    }
}