package com.fphoenixcorneae.jetpackmvvm.base.application

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateReceiver

/**
 * @desc：BaseApplication
 * @date：2021-04-11 17:27
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        registerReceiver(
            NetworkStateReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }
}