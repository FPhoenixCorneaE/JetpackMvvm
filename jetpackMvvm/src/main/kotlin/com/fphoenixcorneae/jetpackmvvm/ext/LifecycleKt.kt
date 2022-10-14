package com.fphoenixcorneae.jetpackmvvm.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*

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

/**
 * 调用协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> ViewModel.launch(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用IO协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> ViewModel.launchIO(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> LifecycleOwner.launch(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    lifecycleScope.launch {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用IO协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> LifecycleOwner.launchIO(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    lifecycleScope.launch(Dispatchers.IO) {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用IO协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> LifecycleOwner.launchMain(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    lifecycleScope.launch(Dispatchers.Main) {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> Fragment.launch(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    viewLifecycleOwner.lifecycleScope.launch {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用IO协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> Fragment.launchIO(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }

/**
 * 调用IO协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> Fragment.launchMain(
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> T,
): Job =
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }