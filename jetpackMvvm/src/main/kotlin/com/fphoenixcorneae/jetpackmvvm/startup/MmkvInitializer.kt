package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.Context
import androidx.startup.Initializer
import com.fphoenixcorneae.ext.logd
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @desc：Startup 初始化 MMKV
 * @since：2021-05-26 14:40
 */
class MmkvInitializer : Initializer<Unit>, CoroutineScope by MainScope() {

    override fun create(context: Context) {
        launch(Dispatchers.IO) {
            // MMKV 初始化
            "MMKV 初始化".logd("startup")
            MMKV.initialize(context)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return mutableListOf()
    }
}