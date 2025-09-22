package com.hen.data.repository


import com.hen.data.db.dao.FixturesDao
import com.hen.data.mapper.toDomain
import com.hen.data.mapper.toEntity
import com.hen.data.network.ApiService
import com.hen.domain.model.Fixture
import com.hen.domain.repository.FixtureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FixtureRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val fixturesDao: FixturesDao
) : FixtureRepository {
    override fun getFixtures(): Flow<List<Fixture>> = fixturesDao.getAllFixtures()
        .map { fixturesCached -> fixturesCached.map { it.toDomain() } }
        .onStart { if (fixturesDao.count() == 0) runCatching { refreshFixtures() } }


    override suspend fun refreshFixtures() {
        val fixturesResponseData = apiService.getFixtures().data
        val entities = fixturesResponseData.map { it.toDomain().toEntity() }
        fixturesDao.saveFixtures(entities)
    }
}