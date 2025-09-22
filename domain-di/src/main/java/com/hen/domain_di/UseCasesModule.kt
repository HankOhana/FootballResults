package com.hen.domain_di

import com.hen.domain.repository.FixtureRepository
import com.hen.domain.usecase.GetFixturesUseCase
import com.hen.domain.usecase.RefreshFixturesUseCase
import com.hen.domain.usecase.getFixtures
import com.hen.domain.usecase.refreshFixtures
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    fun provideGetFixturesUseCase(fixtureRepository: FixtureRepository): GetFixturesUseCase =
        GetFixturesUseCase { getFixtures(fixtureRepository) }

    @Provides
    fun provideRefreshFixturesUseCase(fixtureRepository: FixtureRepository) =
        RefreshFixturesUseCase { refreshFixtures(fixtureRepository) }
}