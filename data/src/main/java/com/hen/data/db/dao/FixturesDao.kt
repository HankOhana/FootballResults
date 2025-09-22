package com.hen.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.hen.data.db.model.FixturesCached
import kotlinx.coroutines.flow.Flow

@Dao
interface FixturesDao {

    @Query("SELECT * FROM FixturesCached")
    fun getAllFixtures(): Flow<List<FixturesCached>>

    @Upsert
    suspend fun saveFixtures(fixtures: List<FixturesCached>)

    @Query("SELECT COUNT(*) FROM FixturesCached")
    suspend fun count(): Int
}