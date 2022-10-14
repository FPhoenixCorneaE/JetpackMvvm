package com.fphoenixcorneae.jetpackmvvm.ext

import com.fphoenixcorneae.jetpackmvvm.base.application.BaseApplication
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.NetworkViewModel

/**
 * NetworkViewModel, 监听网络连接状态, 全局可使用
 */
val networkViewModel by lazy {
    BaseApplication.getInstance().getAndroidViewModel(NetworkViewModel::class.java)
}


