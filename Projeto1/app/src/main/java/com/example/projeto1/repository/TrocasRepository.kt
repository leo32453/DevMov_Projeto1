package com.example.projeto1.repository

import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.retrofit.RetrofitInstance

class TrocasRepository {
    suspend fun getTrocas(): List<ExchangeData> {
        return RetrofitInstance.ExchangeApi.getTrocas()
    }
}