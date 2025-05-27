package com.example.projeto1.repository

import com.example.projeto1.repository.room.SavedLogin
import com.example.projeto1.repository.room.SavedLoginDao
import kotlinx.coroutines.flow.Flow


class SavedLoginRepository(private val dao: SavedLoginDao) {

    suspend fun insert(savedLogin: SavedLogin) = dao.insert(savedLogin)

    suspend fun deleteAll() = dao.deleteAll()

    fun getAll() : Flow<List<SavedLogin>> = dao.getAllInFlow()

}