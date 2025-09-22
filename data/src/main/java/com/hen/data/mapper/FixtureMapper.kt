package com.hen.data.mapper


import com.hen.data.db.model.FixturesCached
import com.hen.data.model.FixtureDto
import com.hen.domain.model.Fixture

fun FixtureDto.toDomain() = Fixture(
    id = id,
    name = name,
    resultInfo = result_info,
    startingAt = starting_at
)

fun FixturesCached.toDomain() = Fixture(
    id = id,
    name = name,
    resultInfo = resultInfo,
    startingAt = startingAt
)

fun Fixture.toEntity() = FixturesCached(
    id = id,
    name = name,
    resultInfo = resultInfo,
    startingAt = startingAt
)