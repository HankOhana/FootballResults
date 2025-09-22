package com.hen.presentation.ui.fixtures

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.hen.domain.model.Fixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class FixtureItemTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `when FixtureItem renders, then text is displayed and click handles`() {
        val clicked = mutableListOf<Int>()
        val f = Fixture(42, "Match Z", "3-2", "2025-02-01T12:00Z")

        compose.setContent {
            FixtureItem(
                fixture = f,
                onClick = { clicked += f.id }
            )
        }

        compose.onNodeWithTag("FixtureItem:${f.id}").assertIsDisplayed()
        compose.onNodeWithText("Match Z").assertIsDisplayed()
        compose.onNodeWithText("3-2").assertIsDisplayed()
        compose.onNodeWithText("2025-02-01T12:00Z").assertIsDisplayed()

        compose.onNodeWithTag("FixtureItem:${f.id}").performClick()
        assertTrue(clicked.contains(42))
    }
}