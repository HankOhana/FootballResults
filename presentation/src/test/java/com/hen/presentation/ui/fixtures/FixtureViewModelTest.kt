package com.hen.presentation.ui.fixtures

import app.cash.turbine.test
import com.hen.domain.model.Fixture
import com.hen.domain.usecase.GetFixturesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs

class FixtureViewModelTest {

    @Test
    fun `given success from use case, when collecting uiState, then Loading then Success`() = runTest {
        val fixtures = listOf(Fixture(1, "A", "1-0", "t1"))
        val getFixturesUseCase = mockk<GetFixturesUseCase>()
        every { getFixturesUseCase.invoke() } returns flowOf(Result.success(fixtures))

        val vm = FixtureViewModel(getFixturesUseCase)

        vm.uiState.test {
            assertIs<FixturesUiState.Loading>(awaitItem())
            assertIs<FixturesUiState.Success>(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given failure from use case, when collecting uiState, then Loading then Error`() = runTest {
        val getFixturesUseCase = mockk<GetFixturesUseCase>()
        every { getFixturesUseCase.invoke() } returns flowOf(Result.failure(IllegalStateException("boom")))

        val vm = FixtureViewModel(getFixturesUseCase)

        vm.uiState.test {
            assertIs<FixturesUiState.Loading>(awaitItem())
            assertIs<FixturesUiState.Error>(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}