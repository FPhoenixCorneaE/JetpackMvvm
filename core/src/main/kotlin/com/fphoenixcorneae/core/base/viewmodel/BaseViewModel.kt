package com.fphoenixcorneae.core.base.viewmodel

import androidx.lifecycle.ViewModel
import com.fphoenixcorneae.core.state.SingleLiveEvent

/**
 * @desc：ViewModel 的基类
 * @date：2021/4/4 18:10
 */
open class BaseViewModel() : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知 Activity/fragment 显示隐藏加载框
     * 跟网络请求显示隐藏 loading 配套
     */
    inner class UiLoadingChange {
        // 显示加载框
        val showDialog by lazy { SingleLiveEvent<String>() }

        // 隐藏
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
    }
}

