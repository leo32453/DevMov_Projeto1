package com.example.projeto1

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.room.AppDatabase

class AppApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        Log.i("AppApplication", "onCreate")
    }
}

class AppContainer(private val context: Context) {
    val savedLoginRepository : SavedLoginRepository by lazy {
        SavedLoginRepository(AppDatabase.getDatabase(context as Application).savedLoginDao())
    }
}