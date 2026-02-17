package com.hen.presentation.ui.fixtures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hen.presentation.R
import com.hen.presentation.ui.fixtures.model.FixtureUiModel

@Composable
fun FixtureScreen(
    viewModel: FixtureViewModel = hiltViewModel(),
    onFixtureClicked: (fixtureId: Int) -> Unit
) {
    val fixturesState by viewModel.uiState.collectAsStateWithLifecycle()

    FixtureScreenContent(
        fixturesState = fixturesState,
        onFixtureClicked = onFixtureClicked
    )
}

@Composable
fun FixtureScreenContent(
    fixturesState: FixturesUiState,
    onFixtureClicked: (fixtureId: Int) -> Unit
) {
    when (fixturesState) {
        is FixturesUiState.Loading -> LoadingFixturesUiState()
        is FixturesUiState.Error -> ErrorFixturesUiState(fixturesState.message)
        is FixturesUiState.Success -> SuccessFixturesUiState(
            fixturesState.fixtures, onFixtureClicked
        )
    }
}

@Composable
fun LoadingFixturesUiState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("LoadingFixturesUiState"),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(66.dp))
    }
}

@Composable
fun ErrorFixturesUiState(message: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("ErrorFixturesUiState"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message ?: stringResource(R.string.unknown_error),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
    }
}

@Composable
fun SuccessFixturesUiState(
    fixtures: List<FixtureUiModel>,
    onFixtureClicked: (fixtureId: Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        listFixtureItems(
            items = fixtures,
            onItemClick = { fixture -> onFixtureClicked(fixture.id) },
            modifierCardItem = Modifier.fillMaxWidth()
        )
    }
}