package com.hen.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.stateInWhileSubscribed(
    scope: CoroutineScope,
    initialValue: T,
    timeout: Long = 5_000L
): StateFlow<T> = this.stateIn(
    scope = scope,
    initialValue = initialValue,
    started = SharingStarted.WhileSubscribed(timeout)
)