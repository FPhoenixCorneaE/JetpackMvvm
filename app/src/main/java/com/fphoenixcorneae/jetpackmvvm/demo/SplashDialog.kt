package com.fphoenixcorneae.jetpackmvvm.demo

import androidx.lifecycle.lifecycleScope
import com.fphoenixcorneae.common.ext.screenHeight
import com.fphoenixcorneae.common.ext.screenWidth
import com.fphoenixcorneae.jetpackmvvm.base.dialog.BaseDialog
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.DialogSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashDialog : BaseDialog<DialogSplashBinding>() {
    override fun DialogSplashBinding.initViewBinding() {
    }

    override fun DialogSplashBinding.initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            dismiss()
        }
    }

    override fun getWidth() = screenWidth

    override fun getHeight() = screenHeight

    override var canceledOnTouchOutside: Boolean = true
    override fun isCancelable(): Boolean = false
}