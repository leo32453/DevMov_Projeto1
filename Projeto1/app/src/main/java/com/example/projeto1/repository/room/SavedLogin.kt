package com.example.projeto1.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedLogin(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val username : String,
    val password : String
)