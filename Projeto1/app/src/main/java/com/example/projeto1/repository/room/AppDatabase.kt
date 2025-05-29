package com.example.projeto1.repository.room
import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
    cria banco de dados utilizando o SavedLogin
 */
@Database(entities = [SavedLogin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedLoginDao(): SavedLoginDao

    companion object {

        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Application): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}