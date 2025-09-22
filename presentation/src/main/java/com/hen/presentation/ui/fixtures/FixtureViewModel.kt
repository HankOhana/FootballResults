package com.hen.presentation.ui.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hen.core.utils.stateInWhileSubscribed
import com.hen.domain.model.Fixture
import com.hen.domain.usecase.GetFixturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FixtureViewModel @Inject constructor(
    getFixturesUseCase: GetFixturesUseCase,
) : ViewModel() {
    val uiState: StateFlow<FixturesUiState> = getFixturesUseCase()
        .map { result ->
            result.fold(
                onSuccess = { fixtures -> FixturesUiState.Success(fixtures) },
                onFailure = { error -> FixturesUiState.Error(error.message) }
            )
        }
        .stateInWhileSubscribed(viewModelScope, FixturesUiState.Loading)
}

sealed interface FixturesUiState {
    data object Loading : FixturesUiState
    data class Success(val fixtures: List<Fixture>) : FixturesUiState
    data class Error(val message: String?) : FixturesUiState
}