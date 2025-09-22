package com.hen.presentation.ui.fixture_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FixtureDetailsScreen(
    viewModel: FixtureDetailsViewModel,
    onBackClicked: () -> Unit,
) {
    val fixtureId = viewModel.uiState.collectAsStateWithLifecycle()

    FixtureDetailsScreenContent(fixtureId = fixtureId.value, onBackClicked = onBackClicked)
}

@Composable
fun FixtureDetailsScreenContent(fixtureId: Int, onBackClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Text(text = "Fixture Details for ID: $fixtureId")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBackClicked) { Text(text = "Back") }
    }
}