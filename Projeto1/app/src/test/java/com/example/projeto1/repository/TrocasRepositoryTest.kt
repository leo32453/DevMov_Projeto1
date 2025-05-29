package com.example.projeto1.repository

import com.example.projeto1.repository.retrofit.ExchangeData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class TrocasRepositoryTest {

    private val repository = TrocasRepository(true)

    @Test
    fun testGetTrocas() = runBlocking {
        val trocas = repository.getTrocas()

        assertNotNull(trocas)
        assertTrue(trocas is List<ExchangeData>)
    }

    @Test
    fun testPostTroca() = runBlocking {
        val exchangeId = (1000..9999).random()
        val novaTroca = ExchangeData(
            id = null,
            exchange_id = exchangeId,
            solicitor_id = 1,
            book_name = "Livro 1",
            book_state =  "Conservado",
            searching_for = "Fantasia",
            sugested = "Livro 2",
            offerings =  emptyList(),
        )

        repository.postTroca(novaTroca)

        val resultado = repository.getTrocaByExchangeId(exchangeId)

        assertTrue(resultado.isNotEmpty())
        assertEquals(1, resultado[0].solicitor_id)
        assertEquals("Livro 1", resultado[0].book_name)
    }

    @Test
    fun testGetMinhasTrocas() = runBlocking {
        val trocas = repository.getMinhasTrocas("1")

        assertNotNull(trocas)
        assertTrue(trocas.all { it.solicitor_id == 1 })
    }

    @Test
    fun testAddOfferToExchange() = runBlocking {
        val exchangeId = 10000

        // Criando uma troca com esse ID
        val troca = ExchangeData(
            id = null,
            exchange_id = exchangeId,
            solicitor_id = 1,
            book_name = "Livro 1",
            book_state =  "Conservado",
            searching_for = "Fantasia",
            sugested = "Livro 2",
            offerings =  emptyList(),
        )

        repository.postTroca(troca)

        // Adicionando uma oferta
        repository.addOfferToExchange(
            exchangeId = exchangeId,
            bookName = "Livro Novo",
            bookState = "Usado",
            userId = 999
        )

        val atualizada = repository.getTrocaByExchangeId(exchangeId)

        assertTrue(atualizada[0].offerings.any { it.userId == 999 && it.book == "Livro Novo" })
    }
}
