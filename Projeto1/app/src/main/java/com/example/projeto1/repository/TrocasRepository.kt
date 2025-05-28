package com.example.projeto1.repository

import com.example.projeto1.repository.retrofit.ExchangeData
import com.example.projeto1.repository.retrofit.RetrofitInstance
import com.example.projeto1.repository.room.SavedLogin
import kotlinx.coroutines.flow.Flow

class TrocasRepository {
    suspend fun getTrocas(): List<ExchangeData> {
        return RetrofitInstance.ExchangeApi.getTrocas()
    }
//    suspend fun getMinhasTrocas(solicitor_id : String): List<ExchangeData> {
//        return RetrofitInstance.ExchangeApi.getMinhasTrocas(solicitor_id)
//    }
//    fun getAll() : Flow<List<SavedLogin>> = dao.getAllInFlow()
    suspend fun getMinhasTrocas(solicitor_id : String): List<ExchangeData> {
        return RetrofitInstance.ExchangeApi.getMinhasTrocas(solicitor_id)
    }
}