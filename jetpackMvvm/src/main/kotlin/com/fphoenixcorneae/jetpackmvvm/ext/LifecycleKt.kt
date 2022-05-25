package com.fphoenixcorneae.jetpackmvvm.ext

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

inline fun ComponentActivity.launchRepeatOnLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

inline fun Fragment.launchRepeatOnLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}