@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hen.data.repository

import app.cash.turbine.test
import com.hen.data.db.dao.FixturesDao
import com.hen.data.db.model.FixturesCached
import com.hen.data.model.FixtureDto
import com.hen.data.network.ApiService
import com.hen.data.network.FixturesResponse
import com.hen.domain.model.Fixture
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class FixtureRepositoryImplTest {

    private val api: ApiService = mockk(relaxed = true)
    private val dao: FixturesDao = mockk(relaxed = true)

    private lateinit var repo: FixtureRepositoryImpl

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = FixtureRepositoryImpl(api, dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `given empty cache, when collecting, then API is called and DAO is saved`() = runTest {
        coEvery { dao.count() } returns 0
        every { dao.getAllFixtures() } returns flowOf(emptyList())

        val dtoA = FixtureDto(id = 1, name = "A", result_info = "2-1", starting_at = "t1")
        val dtoB = FixtureDto(id = 2, name = "B", result_info = "0-0", starting_at = "t2")
        coEvery { api.getFixtures() } returns FixturesResponse(data = listOf(dtoA, dtoB))

        repo.getFixtures().test {
            cancelAndIgnoreRemainingEvents()
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { api.getFixtures() }
        coVerify(exactly = 1) {
            dao.saveFixtures(
                match { saved ->
                    saved.size == 2 &&
                            saved.any { it.id == 1 && it.name == "A" && it.resultInfo == "2-1" && it.startingAt == "t1" } &&
                            saved.any { it.id == 2 && it.name == "B" && it.resultInfo == "0-0" && it.startingAt == "t2" }
                }
            )
        }
    }

    @Test
    fun `given non-empty cache, when collecting, then API is NOT called`() = runTest {
        coEvery { dao.count() } returns 1
        val cached = listOf(FixturesCached(1, "Cached", "1-0", "t0"))
        every { dao.getAllFixtures() } returns flowOf(cached)

        val items = mutableListOf<List<Fixture>>()
        repo.getFixtures().test {
            items += awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
        advanceUntilIdle()

        coVerify(exactly = 0) { api.getFixtures() }
        assertEquals(1, items.single().size)
        assertEquals("Cached", items.single()[0].name)
    }

    @Test
    fun `given empty cache and API throws, when collecting, then API tried once but flow still emits from DAO`() = runTest {
        coEvery { dao.count() } returns 0
        every { dao.getAllFixtures() } returns flowOf(emptyList())
        coEvery { api.getFixtures() } throws RuntimeException("network down")

        repo.getFixtures().test {
            val first = awaitItem()
            assertEquals(emptyList<Fixture>(), first)
            cancelAndIgnoreRemainingEvents()
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { api.getFixtures() }
        coVerify(exactly = 0) { dao.saveFixtures(any()) }
    }

    @Test
    fun `given API returns fixtures, when refreshFixtures is called, then entities are saved to DAO`() = runTest {
        val dtoA = FixtureDto(id = 1, name = "A", result_info = "2-1", starting_at = "t1")
        val dtoB = FixtureDto(id = 2, name = "B", result_info = "0-0", starting_at = "t2")
        coEvery { api.getFixtures() } returns FixturesResponse(data = listOf(dtoA, dtoB))

        repo.refreshFixtures()

        coVerify(exactly = 1) {
            dao.saveFixtures(
                listOf(
                    FixturesCached(1, "A", "2-1", "t1"),
                    FixturesCached(2, "B", "0-0", "t2"),
                )
            )
        }
    }

    @Test
    fun `given API throws exception, when refreshFixtures is called, then exception is propagated`() = runTest {
        coEvery { api.getFixtures() } throws RuntimeException("network error")

        assertFailsWith<RuntimeException> {
            repo.refreshFixtures()
        }
        coVerify(exactly = 0) { dao.saveFixtures(any()) }
    }

    @Test
    fun `given DAO throws exception, when refreshFixtures is called, then exception is propagated`() = runTest {
        val dto = FixtureDto(id = 1, name = "A", result_info = "2-1", starting_at = "t1")
        coEvery { api.getFixtures() } returns FixturesResponse(listOf(dto))
        coEvery { dao.saveFixtures(any()) } throws IllegalStateException("db locked")

        assertFailsWith<IllegalStateException> {
            repo.refreshFixtures()
        }
        coVerify(exactly = 1) { api.getFixtures() }
        coVerify(exactly = 1) { dao.saveFixtures(any()) }
    }

    @Test
    fun `given API returns empty list, when refreshFixtures is called, then empty list is saved`() = runTest {
        coEvery { api.getFixtures() } returns FixturesResponse(emptyList())

        repo.refreshFixtures()

        coVerify(exactly = 1) { dao.saveFixtures(match { it.isEmpty() }) }
    }
}