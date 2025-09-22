package com.hen.domain.repository

import com.hen.domain.model.Fixture
import kotlinx.coroutines.flow.Flow

interface FixtureRepository {
    fun getFixtures(): Flow<List<Fixture>>

    suspend fun refreshFixtures()
}
