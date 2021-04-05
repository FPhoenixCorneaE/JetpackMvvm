package com.fphoenixcorneae.core.network

import com.fphoenixcorneae.core.callback.livedata.UnPeekLiveData

/**
 * @desc：网络变化管理者
 * @date：2021/4/5 19:16
 */
object NetworkStateManager {

    val networkState = UnPeekLiveData<NetWorkState>()

    init {
        // networkState 值为 null 时或者,不为空但是没有网络时才能初始化设值有网络
        if (networkState.value?.isConnected == false) {
            networkState.postValue(NetWorkState(isConnected = true))
        }
    }
}