package com.hen.presentation.ui.fixtures

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.hen.domain.model.Fixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ListFixtureItemsTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun `when listFixtureItems renders, then items displayed and clicks handle`() {
        val fixtures = listOf(
            Fixture(1, "A", "1-0", "t1"),
            Fixture(2, "B", "0-0", "t2"),
        )
        val clicks = mutableListOf<Int>()

        compose.setContent {
            LazyColumn {
                listFixtureItems(
                    items = fixtures,
                    onItemClick = { clicks += it.id },
                    modifierCardItem = Modifier
                )
            }
        }

        compose.onNodeWithTag("FixtureItem:1").assertIsDisplayed()
        compose.onNodeWithTag("FixtureItem:2").assertIsDisplayed()

        compose.onNodeWithTag("FixtureItem:1").performClick()
        compose.onNodeWithTag("FixtureItem:2").performClick()

        assertEquals(listOf(1, 2), clicks)
    }
}