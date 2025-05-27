package com.example.projeto1.repository.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiInterface {
    @GET("users")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): List<ResponseData>
}