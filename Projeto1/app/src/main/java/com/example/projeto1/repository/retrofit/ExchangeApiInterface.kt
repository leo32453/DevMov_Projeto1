package com.example.projeto1.repository.retrofit

import retrofit2.http.GET

interface ExchangeApiInterface {
    @GET("trocas")
    suspend fun getTrocas(): List<ExchangeData>
}