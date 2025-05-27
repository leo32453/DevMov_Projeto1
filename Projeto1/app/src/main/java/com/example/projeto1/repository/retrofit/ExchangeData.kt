package com.example.projeto1.repository.retrofit

data class ExchangeData(
    val exchange_id: Int,
    val solicitor_id: Int,
    val book_name: String,
    val book_state: String,
    val searching_for: String,
    val sugested: String,
    val offerings: List<Offering>
)

data class Offering(
    val userId: Int,
    val book: String
)