package com.hen.domain.usecase

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RefreshFixturesUseCaseTest {

    @Test
    fun `given repository succeeds, when refreshFixtures called, then returns success and calls repo once`() =
        runTest {
            val repo = FakeFixtureRepository(onRefreshFixtures = { /* no-op success */ })

            val result = refreshFixtures(repo)

            assertTrue(result.isSuccess)
            assertEquals(1, repo.refreshCalled)
        }

    @Test
    fun `given repository throws generic error, when refreshFixtures called, then returns failure with same message`() =
        runTest {
            val repo = FakeFixtureRepository(onRefreshFixtures = { throw RuntimeException("boom") })

            val result = refreshFixtures(repo)

            assertTrue(result.isFailure)
            assertEquals("boom", result.exceptionOrNull()?.message)
            assertEquals(1, repo.refreshCalled)
        }

    @Test
    fun `given repository hits timeout, when refreshFixtures called, then returns failure TimeoutCancellationException`() =
        runTest {
            val repo = FakeFixtureRepository(onRefreshFixtures = {
                withTimeout(1) {
                    delay(2)
                }
            })

            val result = refreshFixtures(repo)

            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is TimeoutCancellationException)
            assertEquals(1, repo.refreshCalled)
        }

    @Test
    fun `given repository is cancelled, when refreshFixtures called, then CancellationException is rethrown`() =
        runTest {
            val repo =
                FakeFixtureRepository(onRefreshFixtures = { throw CancellationException("cancelled") })

            assertFailsWith<CancellationException> {
                refreshFixtures(repo)
            }
            assertEquals(1, repo.refreshCalled)
        }
}