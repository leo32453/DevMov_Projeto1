package com.example.projeto1.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest {

    val userRepository = UserRepository(true)

    @Test
    fun loginSuccessful() = runBlocking {
        val (status, id) = userRepository.login("maria@email.com", "123456")
        assertEquals("success", status)
    }

    @Test
    fun loginFail() = runBlocking {
        val (status, id) = userRepository.login("maria", "wrong")
        assertEquals("wrong_username_or_password", status)
    }

}