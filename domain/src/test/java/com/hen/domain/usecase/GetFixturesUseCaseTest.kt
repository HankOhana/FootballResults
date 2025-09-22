package com.hen.domain.usecase

import app.cash.turbine.test
import com.hen.domain.model.Fixture
import com.hen.domain.repository.FixtureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetFixturesUseCaseTest {

    private val sampleFixtures = listOf(
        Fixture(id = 1, name = "Match A", resultInfo = "2-1", startingAt = "t1"),
        Fixture(id = 2, name = "Match B", resultInfo = "0-0", startingAt = "t2")
    )

    @Test
    fun `given repository returns fixtures, when getFixtures called, then emits success`() =
        runTest {
            val repo = FakeFixtureRepository(onGetFixtures = { flow { emit(sampleFixtures) } })
            val useCase = GetFixturesUseCase { getFixtures(repo) }

            useCase().test {
                val result = awaitItem()
                assertTrue(result.isSuccess)
                assertEquals(sampleFixtures, result.getOrNull())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given repository throws error, when getFixtures called, then emits failure`() = runTest {
        val repo =
            FakeFixtureRepository(onGetFixtures = { flow { throw RuntimeException("Network error") } })
        val useCase = GetFixturesUseCase { getFixtures(repo) }

        useCase().test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertEquals("Network error", result.exceptionOrNull()?.message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

class FakeFixtureRepository(
    private val onGetFixtures: (() -> Flow<List<Fixture>>)? = null,
    private val onRefreshFixtures: (suspend () -> Unit)? = null
) : FixtureRepository {

    var getCalled = 0
    var refreshCalled = 0

    override fun getFixtures(): Flow<List<Fixture>> {
        getCalled++
        return onGetFixtures?.invoke()
            ?: error("getFixtures not implemented in this test")
    }

    override suspend fun refreshFixtures() {
        refreshCalled++
        onRefreshFixtures?.invoke()
            ?: error("refreshFixtures not implemented in this test")
    }
}