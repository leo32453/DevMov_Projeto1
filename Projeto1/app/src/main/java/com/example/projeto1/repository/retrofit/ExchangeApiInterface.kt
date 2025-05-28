package com.example.projeto1.repository.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeApiInterface {
    @GET("trocas")
    suspend fun getTrocas(): List<ExchangeData>

    @POST("trocas")
    suspend fun postTroca(@Body troca: ExchangeData)

    // Adiciona oferta a uma troca existente
    @PATCH("trocas/{id}")
    @JvmSuppressWildcards
    suspend fun addOfferToExchange(
        @Path("id") internalId: String?,
        @Body updateData: Map<String, Any>
    ): Response<Unit>

    // Busca troca específica por exchange_id
    @GET("trocas")
    suspend fun getTrocaByExchangeId(
        @Query("exchange_id") exchangeId: Int
    ): List<ExchangeData>
}