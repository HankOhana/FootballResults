package com.hen.presentation.ui.fixtures.model

import androidx.compose.runtime.Stable

@Stable
data class FixtureUiModel(
    val id: Int,
    val name: String,
    val resultInfo: String,
    val startingAt: String
)