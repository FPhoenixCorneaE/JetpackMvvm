package com.fphoenixcorneae.jetpackmvvm.startup

import android.content.Context
import androidx.startup.Initializer
import com.fphoenixcorneae.common.CommonInitializer
import com.fphoenixcorneae.common.ext.logd
import com.fphoenixcorneae.jetpackmvvm.uistate.EmptyCallback
import com.fphoenixcorneae.jetpackmvvm.uistate.ErrorCallback
import com.fphoenixcorneae.jetpackmvvm.uistate.LoadingCallback
import com.fphoenixcorneae.jetpackmvvm.uistate.NoNetworkCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir

/**
 * @desc：UiStateInitializer
 * @date：2022/05/09 10:33
 */
class UiStateInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        "LoadSir 初始化".logd("Startup")
        // 界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(NoNetworkCallback())
            // 设置默认加载状态页
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        // dependency on CommonInitializer.
        return mutableListOf(CommonInitializer::class.java)
    }
}