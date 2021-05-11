package com.fphoenixcorneae.jetpackmvvm.base.application

import android.app.Application
import androidx.startup.AppInitializer
import com.fphoenixcorneae.jetpackmvvm.JMInitializer

/**
 * @desc：BaseApplication
 * @date：2021-04-11 17:27
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // startup: initialize component
        AppInitializer.getInstance(this)
            .initializeComponent(JMInitializer::class.java)
    }
}