package com.example.projeto1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.UserRepository
import com.example.projeto1.repository.room.SavedLogin
import kotlinx.coroutines.launch


class LoginViewModel (
    val savedLoginRepository: SavedLoginRepository
): ViewModel() {
    var username by mutableStateOf("")
    var usernameError by mutableStateOf(false)
    var usernameErrorID by mutableIntStateOf(0)
    var password by mutableStateOf("")
    var passwordError by mutableStateOf(false)
    var passwordErrorID by mutableIntStateOf(0)
    var isLoading by mutableStateOf(false)
    var isLoginSuccessful by mutableStateOf(false)

    val userRepository = UserRepository()


    fun performLogin() {
        Log.i("MainViewModel", "Performing Login")
        usernameError = false
        passwordError = false

        username = username.trim()
        if (username == "") {
            usernameError = true
            usernameErrorID = R.string.error_empty_field
            return
        }

        if (password == "") {
            passwordError = true
            passwordErrorID = R.string.error_empty_field
            return
        }

        viewModelScope.launch {
            isLoading = true
            // userRepository handles username/password check, returns status
            val (status, id) = userRepository.login(username, password)
            isLoading = false
            Log.i("MainViewModel", "id: $id, email: $username, status: $status")
            when(status) {
                "success" -> {
                    val login_to_save = SavedLogin(id = id, email = username)
                    savedLoginRepository.insert(login_to_save)

                    Log.i("MainViewModel", "moving to main screen")
                    isLoginSuccessful = true
                }
                "wrong_username_or_password" -> {
                    Log.i("MainViewModel", "wrong username or password")
                    usernameError = true
                    usernameErrorID = R.string.error_wrong_username_or_password
                    passwordError = true
                    passwordErrorID = R.string.error_wrong_username_or_password
                } "failed_connection" -> {
                Log.i("MainViewModel", "failed connection")
            }
                else -> {
                    usernameError = true
                    usernameErrorID = R.string.error
                }
            }
        }
    }

    fun clearLogin() {
        Log.i("MainViewModel", "login cleared")
        username = ""
        password = ""
        usernameError = false
        passwordError = false
    }

    fun createAccount(){
        Log.i("MainViewModel", "Create Account Clicked")
    }

    fun useSavedLogin() {
        var savedLogins = mutableStateListOf<SavedLogin>()
        Log.i("MainViewModel", "init")
        viewModelScope.launch {
            savedLoginRepository.getAll().collect {
                savedLogins.clear()
                it.forEach {
                        v -> savedLogins.add(v)
                    isLoginSuccessful = true
                    Log.i("MainViewModel", "a")}
            }
        }
    }

    suspend fun logout(){
        Log.i("MainViewModel", "Logout Clicked")
        savedLoginRepository.deleteAll()
    }
}