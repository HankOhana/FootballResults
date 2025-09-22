package com.hen.data.db

import android.content.Context
import androidx.room.Room
import com.hen.data.db.dao.FixturesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database",
    ).build()

    @Singleton
    @Provides
    fun provideFixturesDao(database: AppDatabase): FixturesDao = database.fixturesDao()
}