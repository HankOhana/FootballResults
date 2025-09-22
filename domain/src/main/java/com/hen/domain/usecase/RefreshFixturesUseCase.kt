package com.hen.domain.usecase

import com.hen.core.utils.resultOf
import com.hen.domain.repository.FixtureRepository

fun interface RefreshFixturesUseCase : suspend () -> Result<Unit>

suspend fun refreshFixtures(fixtureRepository: FixtureRepository): Result<Unit> = resultOf {
    fixtureRepository.refreshFixtures()
}