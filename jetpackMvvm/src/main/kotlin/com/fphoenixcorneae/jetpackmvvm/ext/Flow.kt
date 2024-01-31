package com.fphoenixcorneae.jetpackmvvm.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Activities and Fragments call
 *
 * Runs the given block in a new coroutine when this Lifecycle is at least
 * at state and suspends the execution until this Lifecycle is Lifecycle.State.DESTROYED.
 * The block will cancel and re-launch as the lifecycle moves in and out of the target state.
 *
 * uiStateFlow.collectWithLifecycle(this) { uiState ->
 *     updateUi(uiState)
 * }
 */
inline fun <reified T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline block: suspend (T) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { block(it) }
}

/**
 * Fragments call
 *
 * Runs the given block in a new coroutine when this Lifecycle is at least
 * at state and suspends the execution until this Lifecycle is Lifecycle.State.DESTROYED.
 * The block will cancel and re-launch as the lifecycle moves in and out of the target state.
 *
 * uiStateFlow.collectWithLifecycle(this) { uiState ->
 *     updateUi(uiState)
 * }
 */
inline fun <reified T> Flow<T>.collectWithLifecycle(
    fragment: Fragment,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline block: suspend (T) -> Unit,
): Job = fragment.viewLifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle, minActiveState).collect { block(it) }
}