package com.fphoenixcorneae.jetpackmvvm

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @desc：Startup 初始化
 * @since：2021-04-29 17:38
 */
class JMInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            // MMKV 初始化
            MMKV.initialize(appContext)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}