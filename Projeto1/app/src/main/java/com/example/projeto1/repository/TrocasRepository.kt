package com.example.projeto1.repository

import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.retrofit.Offering
import com.example.projeto1.repository.retrofit.RetrofitInstance
import com.example.projeto1.repository.retrofit.RetrofitInstance.ExchangeApi

class TrocasRepository {
    suspend fun getTrocas(): List<ExchangeData> {
        return RetrofitInstance.ExchangeApi.getTrocas()
    }

    suspend fun postTroca(troca: ExchangeData) {
        RetrofitInstance.ExchangeApi.postTroca(troca)
    }

    suspend fun addOfferToExchange(
        exchangeId: Int,
        bookName: String,
        bookState: String,
        userId: Int
    ) {
        val currentExchanges = ExchangeApi.getTrocaByExchangeId(exchangeId)

        val currentExchange = currentExchanges.firstOrNull()
            ?: throw Exception("Troca com exchange_id $exchangeId não encontrada.")

        val internalExchangeId: String = currentExchange.id
            ?: throw Exception("ID interno da troca (gerado pelo JSON Server) não encontrado. Verifique se o JSON Server está retornando o campo 'id' e se sua data class está correta.")

        val newOffer = Offering(
            userId = userId,
            book = bookName,
            book_state = bookState
        )
        val updatedOfferings = currentExchange.offerings + newOffer

        val response = ExchangeApi.addOfferToExchange(
            internalId = internalExchangeId,
            updateData = mapOf("offerings" to updatedOfferings)
        )

        if (!response.isSuccessful) {
            throw Exception("Falha ao adicionar oferta: ${response.code()} - ${response.errorBody()?.string()}")
        } else {
            println("Oferta adicionada com sucesso à troca $exchangeId.")
        }
    }


}