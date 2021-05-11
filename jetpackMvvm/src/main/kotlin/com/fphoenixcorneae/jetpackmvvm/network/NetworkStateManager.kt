package com.fphoenixcorneae.jetpackmvvm.network

import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.jetpackmvvm.livedata.Event

/**
 * @desc：网络变化管理者
 * @date：2021/4/5 19:16
 */
object NetworkStateManager {
    val networkState = MutableLiveData<Event<NetWorkState>>()
}