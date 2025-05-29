package com.example.projeto1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.UserRepository
import com.example.projeto1.repository.room.SavedLogin
import kotlinx.coroutines.launch

/*
    Factory: AppViewModelProvider
 */
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
        Log.i("LoginViewModel", "Performing Login")
        usernameError = false
        passwordError = false

        /*
            username having leading and trailing whitespace removed
            and password must be present,

            if there's an error, the screen uses the resource id to
            get the error message
         */
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

        /*
            suspend functions,
            coroutine in a viewmodel for asynchronous  operations
         */
        viewModelScope.launch {
            isLoading = true
            // userRepository handles username/password check, returns status and user id
            val (status, id) = userRepository.login(username, password)
            isLoading = false
            Log.i("LoginViewModel", "id: $id, email: $username, status: $status")
            when(status) {
                // success saves login info locally
                "success" -> {
                    val login_to_save = SavedLogin(id = id, email = username)
                    savedLoginRepository.insert(login_to_save)

                    Log.i("LoginViewModel", "moving to main screen")
                    isLoginSuccessful = true
                }
                "wrong_username_or_password" -> {
                    Log.i("LoginViewModel", "wrong username or password")
                    usernameError = true
                    usernameErrorID = R.string.error_wrong_username_or_password
                    passwordError = true
                    passwordErrorID = R.string.error_wrong_username_or_password
                } "failed_connection" -> {
                Log.i("LoginViewModel", "failed connection")
            }
                else -> {
                    // other error
                    usernameError = true
                    usernameErrorID = R.string.error
                }
            }
        }
    }

//    fun clearLogin() {
//        Log.i("LoginViewModel", "login cleared")
//        username = ""
//        password = ""
//        usernameError = false
//        passwordError = false
//    }

    /*
        not implemented yet
     */
    fun createAccount(){
        Log.i("LoginViewModel", "Create Account Clicked")
    }

    /*
        function to check if theres a saved login,
        to skip the login page
     */
    fun useSavedLogin() {
        Log.i("LoginViewModel", "init")
        viewModelScope.launch {
            val saved = savedLoginRepository.getSaved()
            if (saved != null){
                isLoginSuccessful = true
            }
        }
    }
}