package com.hen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hen.data.db.dao.FixturesDao
import com.hen.data.db.model.FixturesCached

@Database(
    entities = [FixturesCached::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fixturesDao(): FixturesDao
}