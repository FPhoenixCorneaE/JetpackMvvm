package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.Initializer
import com.fphoenixcorneae.ext.logd
import com.fphoenixcorneae.jetpackmvvm.base.application.ApplicationLifecycleObserver
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @desc：Startup 初始化
 * @since：2021-04-29 17:38
 */
class JmInitializer : Initializer<Unit>, CoroutineScope by MainScope() {

    override fun create(context: Context) {
        launch {
            context.registerReceiver(
                NetworkStateReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
            ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationLifecycleObserver)
            "Add ApplicationLifecycleObserver".logd("startup")
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return mutableListOf()
    }
}