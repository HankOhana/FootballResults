package com.hen.domain.usecase


import com.hen.domain.model.Fixture
import com.hen.domain.repository.FixtureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun interface GetFixturesUseCase : () -> Flow<Result<List<Fixture>>>

fun getFixtures(fixtureRepository: FixtureRepository): Flow<Result<List<Fixture>>> =
    fixtureRepository.getFixtures()
        .map { fixtures -> Result.success(fixtures) }
        .catch { e -> emit(Result.failure(e)) }