package com.example.projeto1.repository

import com.example.projeto1.repository.retrofit.LoginApiInterface
import com.example.projeto1.repository.retrofit.RetrofitInstance

class UserRepository(val useTestUrl: Boolean = false) {

    private val client: LoginApiInterface = if (useTestUrl) RetrofitInstance.testapi else RetrofitInstance.api

    suspend fun login(email: String, password: String): Pair<String, Int> {
        return try {
            val users = client.login(email, password)
            if (users.isNotEmpty()) {
                "success" to users[0].id
            } else {
                "wrong_username_or_password" to -1
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "failed_connection" to -1
        }
    }
}