package com.hen.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FixturesCached(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "result_info") val resultInfo: String,
    @ColumnInfo(name = "starting_at") val startingAt: String
)