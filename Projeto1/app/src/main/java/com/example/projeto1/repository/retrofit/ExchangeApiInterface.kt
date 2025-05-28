package com.example.projeto1.repository.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExchangeApiInterface {
    @GET("trocas")
    suspend fun getTrocas(): List<ExchangeData>

    @POST("trocas")
    suspend fun postTroca(@Body troca: ExchangeData)
}