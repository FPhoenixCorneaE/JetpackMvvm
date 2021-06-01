package com.fphoenixcorneae.jetpackmvvm.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.ext.logd

/**
 * @desc：Application 生命周期观察者
 * @date：2021/04/29 11:42
 */
object ApplicationLifecycleImpl : DefaultLifecycleObserver {

    val isForeground = MutableLiveData<Boolean>()
    val isDestroyed = MutableLiveData<Boolean>()

    /**
     * 在前台
     */
    override fun onStart(owner: LifecycleOwner) {
        "onApplicationStarted : ${owner.javaClass.canonicalName}".logd("ApplicationLifecycle")
        isForeground.postValue(true)
    }

    /**
     * 在后台
     */
    override fun onStop(owner: LifecycleOwner) {
        "onApplicationStopped : ${owner.javaClass.canonicalName}".logd("ApplicationLifecycle")
        isForeground.postValue(false)
    }

    /**
     * 应用销毁
     */
    override fun onDestroy(owner: LifecycleOwner) {
        "onApplicationDestroyed : ${owner.javaClass.canonicalName}".logd("ApplicationLifecycle")
        isDestroyed.postValue(true)
    }
}