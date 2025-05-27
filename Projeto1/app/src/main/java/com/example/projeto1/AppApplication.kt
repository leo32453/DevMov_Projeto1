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

/**
 * We want to limit the visibility of Android-related objects to ViewModels and Composable.
 *
 * So, we attach here the repositories to a GameApplication object
 *    so that we can retrieve them in the AppViewModelProvider.
 */
class AppContainer(private val context: Context) {
    val savedLoginRepository : SavedLoginRepository by lazy {
        SavedLoginRepository(AppDatabase.getDatabase(context).savedLoginDao())
    }
}