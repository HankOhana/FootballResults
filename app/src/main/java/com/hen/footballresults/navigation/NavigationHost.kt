package com.hen.footballresults.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hen.presentation.ui.fixture_details.fixtureDetailsScreen
import com.hen.presentation.ui.fixture_details.navigateToFixtureDetails
import com.hen.presentation.ui.fixtures.FixturesRoute
import com.hen.presentation.ui.fixtures.fixturesScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FixturesRoute,
        modifier = modifier,
    ) {
        fixturesScreen(
            onFixtureClicked = { fixtureId ->
                navController.navigateToFixtureDetails(fixtureId)
            }
        )
        fixtureDetailsScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}