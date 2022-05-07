package com.fphoenixcorneae.jetpackmvvm.uistate

import android.content.Context
import android.view.View
import com.fphoenixcorneae.jetpackmvvm.R
import com.kingja.loadsir.callback.Callback

/**
 * @desc：LoadingCallback
 * @date：2022/05/07 17:13
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int = R.layout.jm_layout_ui_state_loading

    override fun onReloadEvent(context: Context?, view: View?): Boolean = true
}