package com.hen.presentation.di

import com.hen.domain.model.Fixture
import com.hen.domain.usecase.GetFixturesUseCase
import com.hen.domain.usecase.RefreshFixturesUseCase
import com.hen.domain_di.UseCasesModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCasesModule::class] // ⬅️ подменяем прод-модуль
)
object FakeUseCasesTestModule {

    @Provides @Singleton
    fun provideGetFixturesUseCase(fake: FakeGetFixturesUseCase): GetFixturesUseCase = fake

    @Provides @Singleton
    fun provideRefreshFixturesUseCase(fake: FakeRefreshFixturesUseCase): RefreshFixturesUseCase = fake
}

@Singleton
class FakeGetFixturesUseCase @Inject constructor() : GetFixturesUseCase {
    private val flow = MutableSharedFlow<Result<List<Fixture>>>(replay = 1)
    override fun invoke(): Flow<Result<List<Fixture>>> = flow
    suspend fun emit(value: Result<List<Fixture>>) = flow.emit(value)
}

@Singleton
class FakeRefreshFixturesUseCase @Inject constructor() : RefreshFixturesUseCase {
    var result: Result<Unit> = Result.success(Unit)
    override suspend fun invoke(): Result<Unit> = result
}