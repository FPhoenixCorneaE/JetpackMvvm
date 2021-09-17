package com.fphoenixcorneae.jetpackmvvm.base.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex

/**
 * @desc：BaseApplication
 * @date：2021-04-11 17:27
 */
open class BaseApplication : Application(), ViewModelStoreOwner {

    private val mViewModelStore by lazy { ViewModelStore() }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }

    /**
     * 获取 Application 上下文的 ViewModel
     * 注意: Application 需要实现 ViewModelStoreOwner 接口.
     */
    fun <VM : ViewModel> getAndroidViewModel(modelClass: Class<VM>): VM {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        ).get(modelClass)
    }

    companion object {
        @Volatile
        private var sInstance: BaseApplication? = null

        @Synchronized
        fun getInstance(): BaseApplication = sInstance!!
    }
}