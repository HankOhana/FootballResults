package com.hen.presentation.ui.fixture_details

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel(assistedFactory = FixtureDetailsViewModel.Factory::class)
class FixtureDetailsViewModel @AssistedInject constructor(
    @Assisted val fixtureId: Int
) : ViewModel() {

    val uiState: StateFlow<Int> = MutableStateFlow(fixtureId).asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(fixtureId: Int): FixtureDetailsViewModel
    }
}