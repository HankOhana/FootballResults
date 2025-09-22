package com.hen.data.di

import com.hen.data.repository.FixtureRepositoryImpl
import com.hen.domain.repository.FixtureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    @Singleton
    fun bindFixtureRepository(fixtureRepositoryImpl: FixtureRepositoryImpl): FixtureRepository
}