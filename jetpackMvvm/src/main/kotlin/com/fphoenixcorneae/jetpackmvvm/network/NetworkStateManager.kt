package com.fphoenixcorneae.jetpackmvvm.network

import com.fphoenixcorneae.jetpackmvvm.livedata.EventLiveData

/**
 * @desc：网络变化管理者
 * @date：2021/4/5 19:16
 */
object NetworkStateManager {
    val networkState = EventLiveData<NetWorkState>()
}