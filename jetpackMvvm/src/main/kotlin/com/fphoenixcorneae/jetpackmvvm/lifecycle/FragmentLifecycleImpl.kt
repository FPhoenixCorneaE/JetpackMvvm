package com.fphoenixcorneae.jetpackmvvm.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.fphoenixcorneae.common.ext.logd

/**
 * @desc：Fragment 生命周期回调
 * @since：2021-06-01 11:17
 */
class FragmentLifecycleImpl : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        "onFragmentCreated : ${owner.javaClass.canonicalName}".logd("FragmentLifecycle")
    }

    override fun onStart(owner: LifecycleOwner) {
        "onFragmentStarted : ${owner.javaClass.canonicalName}".logd("FragmentLifecycle")
    }

    override fun onResume(owner: LifecycleOwner) {
        "onFragmentResumed : ${owner.javaClass.canonicalName}".logd("FragmentLifecycle")
    }

    override fun onPause(owner: LifecycleOwner) {
        "onFragmentPaused : ${owner.javaClass.canonicalName}".logd("FragmentLifecycle")
    }

    override fun onStop(owner: LifecycleOwner) {
        "onFragmentStopped : ${owner.javaClass.canonicalName}".logd("FragmentLifecycle")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        "onFragmentDestroyed : ${owner.javaClass.canonicalName}".logd("FragmentLifecycle")
    }
}