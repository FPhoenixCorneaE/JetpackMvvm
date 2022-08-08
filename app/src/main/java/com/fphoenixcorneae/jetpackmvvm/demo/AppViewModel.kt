package com.fphoenixcorneae.jetpackmvvm.demo

import com.fphoenixcorneae.jetpackmvvm.base.application.BaseApplication

val testFragmentViewModel by lazy {
    BaseApplication.getInstance().getAndroidViewModel(TestFragmentViewModel::class.java)
}