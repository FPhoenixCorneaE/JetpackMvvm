package com.fphoenixcorneae.jetpackmvvm.base.application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent

/**
 * @desc：应用生命周期观察者
 * @date：2021/04/29 11:42
 */
object ApplicationLifecycleObserver : LifecycleObserver {

    /**
     * 应用是否在前台
     */
    var isForeground = MutableLiveData<Boolean>()

    /**
     * 在前台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onForeground() {
        isForeground.value = true
    }

    /**
     * 在后台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        isForeground.value = false
    }
}