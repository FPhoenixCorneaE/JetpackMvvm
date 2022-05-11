package com.fphoenixcorneae.jetpackmvvm.demo

import com.fphoenixcorneae.common.ext.screenHeight
import com.fphoenixcorneae.common.ext.screenWidth
import com.fphoenixcorneae.jetpackmvvm.base.dialog.BaseDialog
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.DialogSplashBinding

class SplashDialog : BaseDialog<DialogSplashBinding>() {
    override fun initViewBinding(): DialogSplashBinding {
        return DialogSplashBinding.inflate(layoutInflater)
    }

    override fun getWidth() = screenWidth

    override fun getHeight() = screenHeight
}