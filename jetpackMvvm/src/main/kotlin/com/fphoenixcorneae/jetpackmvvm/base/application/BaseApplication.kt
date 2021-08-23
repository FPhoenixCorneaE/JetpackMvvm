package com.fphoenixcorneae.jetpackmvvm.base.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * @desc：BaseApplication
 * @date：2021-04-11 17:27
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}