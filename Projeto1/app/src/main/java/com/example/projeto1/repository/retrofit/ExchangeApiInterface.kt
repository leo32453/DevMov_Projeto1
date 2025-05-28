package com.example.projeto1.repository.retrofit

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApiInterface {
    @GET("trocas")
    suspend fun getTrocas(): List<ExchangeData>

    @GET("trocas")
    suspend fun getMinhasTrocas(
        @Query("solicitor_id") solicitor_id: String,
    ): List<ExchangeData>
}