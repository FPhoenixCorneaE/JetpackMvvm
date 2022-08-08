package com.fphoenixcorneae.jetpackmvvm.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.lifecycleScope
import com.fphoenixcorneae.common.ext.logd
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
            delay(1000)
            dismiss()
        }

        setOnDismissListener {
            "SplashDialog is dismiss.".logd()
        }
    }

    override fun getWindowBackground(): Drawable {
        return ColorDrawable(Color.TRANSPARENT)
    }

    override fun getWidth() = screenWidth

    override fun getHeight() = screenHeight

    override fun getDimAmount(): Float {
        return 0.4f
    }

    override var canceledOnTouchOutside: Boolean = true

    override fun isCancelable(): Boolean = false
}