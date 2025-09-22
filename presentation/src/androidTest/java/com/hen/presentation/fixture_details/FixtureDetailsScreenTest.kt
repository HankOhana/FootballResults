package com.hen.presentation.fixture_details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.hen.presentation.TestActivity
import com.hen.presentation.ui.fixture_details.FixtureDetailsScreen
import com.hen.presentation.ui.fixture_details.FixtureDetailsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FixtureDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<TestActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun givenFixtureId_whenScreenShown_thenTextVisible() {
        val fixtureId = 42
        val vm = FixtureDetailsViewModel(fixtureId = fixtureId)

        composeRule.setContent {
            FixtureDetailsScreen(
                viewModel = vm,
                onBackClicked = {}
            )
        }

        composeRule.onNodeWithText("Fixture Details for ID: $fixtureId").assertIsDisplayed()
    }

}