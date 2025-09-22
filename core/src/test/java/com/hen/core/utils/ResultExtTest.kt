package com.hen.core.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Test

class ResultExtTest {

    @Test
    fun `returns success when block succeeds`() {
        val res = resultOf { 42 }
        assertTrue(res.isSuccess)
        assertEquals(42, res.getOrNull())
    }

    @Test
    fun `wraps regular exception into failure`() {
        val ex = IllegalStateException("boom")
        val res = resultOf { throw ex }
        assertTrue(res.isFailure)
        assertSame(ex, res.exceptionOrNull())
    }

    @Test
    fun `wraps TimeoutCancellationException into failure`() {
        val res = resultOf {
            runBlocking {
                withTimeout(1) { delay(23) }
            }
        }
        assertTrue(res.isFailure)
        assertTrue(res.exceptionOrNull() is TimeoutCancellationException)
    }

    @Test(expected = CancellationException::class)
    fun `rethrows CancellationException`() {
        resultOf { throw CancellationException("cancel") }
    }

    @Test(expected = AssertionError::class)
    fun `does not catch Error subclasses`() {
        resultOf { throw AssertionError("bad") }
    }
}