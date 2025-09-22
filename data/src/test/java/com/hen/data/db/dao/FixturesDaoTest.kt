package com.hen.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hen.data.db.AppDatabase
import com.hen.data.db.model.FixturesCached
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class FixturesDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: FixturesDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.fixturesDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `initial state is empty and count is zero`() = runTest {
        val list = dao.getAllFixtures().first()
        assertTrue(list.isEmpty())
        assertEquals(0, dao.count())
    }

    @Test
    fun `saveFixtures inserts items and count matches`() = runTest {
        dao.saveFixtures(
            listOf(
                FixturesCached(id = 1, name = "Match 1", resultInfo = "0-0", startingAt = "2025-09-21T10:00:00Z"),
                FixturesCached(id = 2, name = "Match 2", resultInfo = "1-0", startingAt = "2025-09-21T12:00:00Z"),
            )
        )

        val fromDb = dao.getAllFixtures().first().sortedBy { it.id }
        assertEquals(2, fromDb.size)
        assertEquals(2, dao.count())

        val first = fromDb[0]
        assertEquals(1, first.id)
        assertEquals("Match 1", first.name)
        assertEquals("0-0", first.resultInfo)
    }
}