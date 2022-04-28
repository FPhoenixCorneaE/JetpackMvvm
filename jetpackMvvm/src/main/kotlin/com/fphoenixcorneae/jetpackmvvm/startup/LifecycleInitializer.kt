package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.Initializer
import com.fphoenixcorneae.common.CommonInitializer
import com.fphoenixcorneae.common.ext.applicationContext
import com.fphoenixcorneae.common.ext.logd
import com.fphoenixcorneae.jetpackmvvm.lifecycle.ActivityLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.lifecycle.ApplicationLifecycleImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * @desc：Startup 生命周期回调监听
 * @date：2021/06/01 16:16
 */
class LifecycleInitializer  : Initializer<Unit>, CoroutineScope by MainScope() {

    override fun create(context: Context) {
        "LifecycleObserver 初始化".logd("startup")
        applicationContext.registerActivityLifecycleCallbacks(ActivityLifecycleImpl())
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleImpl)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return mutableListOf(CommonInitializer::class.java)
    }
}