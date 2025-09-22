package com.hen.presentation.ui.fixtures

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object FixturesRoute

fun NavGraphBuilder.fixturesScreen(
    onFixtureClicked: (fixtureId: Int) -> Unit,
) {
    composable<FixturesRoute> {
        FixtureScreen(onFixtureClicked = onFixtureClicked)
    }
}