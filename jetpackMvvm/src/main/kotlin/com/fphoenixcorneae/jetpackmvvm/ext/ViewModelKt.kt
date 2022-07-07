package com.fphoenixcorneae.jetpackmvvm.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fphoenixcorneae.jetpackmvvm.base.application.BaseApplication
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.NetworkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * NetworkViewModel, 监听网络连接状态, 全局可使用
 */
val networkViewModel by lazy {
    BaseApplication.getInstance().getAndroidViewModel(NetworkViewModel::class.java)
}

/**
 * 调用协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调
 */
fun <T> ViewModel.launch(
    block: suspend CoroutineScope.() -> T,
    error: (Throwable) -> Unit = {},
    success: (T) -> Unit = {},
) =
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
    block: suspend CoroutineScope.() -> T,
    error: (Throwable) -> Unit = {},
    success: (T) -> Unit = {},
) =
    viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
