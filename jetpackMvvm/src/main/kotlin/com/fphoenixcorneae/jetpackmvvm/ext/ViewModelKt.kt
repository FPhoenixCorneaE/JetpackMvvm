package com.fphoenixcorneae.jetpackmvvm.ext

import androidx.lifecycle.viewModelScope
import com.fphoenixcorneae.jetpackmvvm.base.application.BaseApplication
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.NetworkViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * NetworkViewModel, 监听网络连接状态, 全局可使用
 */
val networkViewModel by lazy {
    BaseApplication.getInstance().getAndroidViewModel(NetworkViewModel::class.java)
}

/**
 *  调用协程
 * @param block   操作耗时操作任务
 * @param success 成功回调
 * @param error   失败回调 可不给
 */
fun <T> BaseViewModel.launchIO(
    block: () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}