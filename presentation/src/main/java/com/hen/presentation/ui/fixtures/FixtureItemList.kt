package com.hen.presentation.ui.fixtures

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.hen.domain.model.Fixture


fun LazyListScope.listFixtureItems(
    items: List<Fixture>,
    onItemClick: (Fixture) -> Unit,
    modifierCardItem: Modifier = Modifier
) = items(
    items = items,
    key = { it.id },
    itemContent = { fixture ->
        FixtureItem(fixture, onClick = { onItemClick(fixture) }, modifier = modifierCardItem)
    })