package com.hen.data.mapper

import com.hen.data.db.model.FixturesCached
import com.hen.data.model.FixtureDto
import com.hen.domain.model.Fixture
import org.junit.Assert.assertEquals
import org.junit.Test

class FixtureMappersTest {

    @Test
    fun `FixtureDto toDomain maps snake_case fields correctly`() {
        val dto = FixtureDto(
            id = 7,
            name = "Team A vs Team B",
            result_info = "2-1",
            starting_at = "2025-09-21T10:00:00Z"
        )

        val domain = dto.toDomain()

        assertEquals(7, domain.id)
        assertEquals("Team A vs Team B", domain.name)
        assertEquals("2-1", domain.resultInfo)
        assertEquals("2025-09-21T10:00:00Z", domain.startingAt)
    }

    @Test
    fun `FixturesCached toDomain maps fields 1-1`() {
        val cached = FixturesCached(
            id = 42,
            name = "Match",
            resultInfo = "0-0",
            startingAt = "2025-09-21T12:00:00Z"
        )

        val domain = cached.toDomain()

        assertEquals(42, domain.id)
        assertEquals("Match", domain.name)
        assertEquals("0-0", domain.resultInfo)
        assertEquals("2025-09-21T12:00:00Z", domain.startingAt)
    }

    @Test
    fun `Fixture toEntity then back toDomain preserves data`() {
        val original = Fixture(
            id = 101,
            name = "Derby",
            resultInfo = "3-3",
            startingAt = "2025-09-21T21:00:00Z"
        )

        val entity = original.toEntity()
        val roundTrip = entity.toDomain()

        assertEquals(original.id, roundTrip.id)
        assertEquals(original.name, roundTrip.name)
        assertEquals(original.resultInfo, roundTrip.resultInfo)
        assertEquals(original.startingAt, roundTrip.startingAt)
    }
}