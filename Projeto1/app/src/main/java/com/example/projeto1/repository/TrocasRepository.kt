package com.example.projeto1.repository

import com.example.projeto1.repository.retrofit.ExchangeApiInterface
import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.retrofit.LoginApiInterface
import com.example.projeto1.repository.retrofit.Offering
import com.example.projeto1.repository.retrofit.RetrofitInstance
import com.example.projeto1.repository.retrofit.RetrofitInstance.ExchangeApi

class TrocasRepository (val useTestUrl: Boolean = false) {

    private val client: ExchangeApiInterface = if (useTestUrl) RetrofitInstance.TestExchangeApi else RetrofitInstance.ExchangeApi

    suspend fun getTrocas(): List<ExchangeData> {
        return client.getTrocas()
    }

    suspend fun getMinhasTrocas(solicitor_id : String): List<ExchangeData> {
        return client.getMinhasTrocas(solicitor_id)
    }

    suspend fun getTrocaByExchangeId(exchange_id : Int): List<ExchangeData> {
        return client.getTrocaByExchangeId(exchange_id)
    }

    suspend fun postTroca(troca: ExchangeData) {
        client.postTroca(troca)
    }

    suspend fun addOfferToExchange(
        exchangeId: Int,
        bookName: String,
        bookState: String,
        userId: Int
    ) {
        val currentExchanges = client.getTrocaByExchangeId(exchangeId)

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

        val response = client.addOfferToExchange(
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