package com.fphoenixcorneae.jetpackmvvm.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * 生命周期与 Application 一样长的协程，可以做一些后台作业
 */
val globalScope by lazy {
    CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}

/**
 * Runs the block of code in a coroutine when the lifecycle is at least STARTED.
 * The coroutine will be cancelled when the ON_STOP event happens and will
 * restart executing if the lifecycle receives the ON_START event again.
 */
inline fun LifecycleOwner.launchRepeatOnLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    lifecycleScope.launch {
        repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

/**
 * Runs the block of code in a coroutine when the lifecycle is at least STARTED.
 * The coroutine will be cancelled when the ON_STOP event happens and will
 * restart executing if the lifecycle receives the ON_START event again.
 */
inline fun Fragment.launchRepeatOnLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}