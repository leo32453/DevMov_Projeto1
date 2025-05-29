package com.example.projeto1

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projeto1.repository.room.AppDatabase
import com.example.projeto1.repository.room.SavedLogin
import com.example.projeto1.repository.room.SavedLoginDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith


import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var dao: SavedLoginDao
    private lateinit var db: AppDatabase

    val SAVED = SavedLogin(id = 1, email = "maria@email.com")


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        dao = db.savedLoginDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testEmpty() = runTest {
        val saved = dao.getSaved()
        assertNull(saved)
    }

    @Test
    fun testInsertAndSelect() = runTest {
        dao.insert(SAVED)
        val saved = dao.getSaved()
        // saved has id 1 email maria@email.com
        assertEquals(SAVED, saved)
    }
}