package com.hen.presentation.ui.fixture_details

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class FixtureDetailsRoute(val fixtureId: Int)

fun NavController.navigateToFixtureDetails(fixtureId: Int) =
    navigate(route = FixtureDetailsRoute(fixtureId))

fun NavGraphBuilder.fixtureDetailsScreen(
    onBackClick: () -> Unit,
) {
    composable<FixtureDetailsRoute> { backStackEntry ->
        val fixtureId = backStackEntry.toRoute<FixtureDetailsRoute>().fixtureId
        val viewModel =
            hiltViewModel<FixtureDetailsViewModel, FixtureDetailsViewModel.Factory> { factory ->
                factory.create(fixtureId)
            }
        FixtureDetailsScreen(viewModel = viewModel, onBackClicked = onBackClick)
    }
}