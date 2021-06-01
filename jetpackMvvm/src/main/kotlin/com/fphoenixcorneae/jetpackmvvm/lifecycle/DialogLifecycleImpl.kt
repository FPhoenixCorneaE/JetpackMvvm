package com.fphoenixcorneae.jetpackmvvm.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.fphoenixcorneae.ext.logd

/**
 * @desc：Dialog 生命周期回调
 * @since：2021-06-01 11:17
 */
class DialogLifecycleImpl : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        "onDialogCreated : ${owner.javaClass.canonicalName}".logd("DialogLifecycle")
    }

    override fun onStart(owner: LifecycleOwner) {
        "onDialogStarted : ${owner.javaClass.canonicalName}".logd("DialogLifecycle")
    }

    override fun onResume(owner: LifecycleOwner) {
        "onDialogResumed : ${owner.javaClass.canonicalName}".logd("DialogLifecycle")
    }

    override fun onPause(owner: LifecycleOwner) {
        "onDialogPaused : ${owner.javaClass.canonicalName}".logd("DialogLifecycle")
    }

    override fun onStop(owner: LifecycleOwner) {
        "onDialogStopped : ${owner.javaClass.canonicalName}".logd("DialogLifecycle")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        "onDialogDestroyed : ${owner.javaClass.canonicalName}".logd("DialogLifecycle")
    }
}