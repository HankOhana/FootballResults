package com.hen.data.network

import com.hen.data.model.FixtureDto
import kotlinx.serialization.Serializable

@Serializable
data class FixturesResponse(val data: List<FixtureDto>)