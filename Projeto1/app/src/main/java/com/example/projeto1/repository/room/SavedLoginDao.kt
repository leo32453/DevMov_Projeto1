package com.example.projeto1.repository.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLoginDao {

    @Insert
    suspend fun insert(loginTry : SavedLogin)

    @Query("DELETE FROM SavedLogin")
    suspend fun deleteAll()

    @Query("Select * from SavedLogin")
    fun getAllInFlow() : Flow<List<SavedLogin>>

}