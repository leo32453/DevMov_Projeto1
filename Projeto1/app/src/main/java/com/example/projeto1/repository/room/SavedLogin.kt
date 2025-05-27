package com.example.projeto1.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedLogin(
    @PrimaryKey()
    val id : Int,
    val email : String
)