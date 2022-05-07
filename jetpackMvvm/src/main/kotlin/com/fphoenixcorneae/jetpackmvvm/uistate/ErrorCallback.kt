package com.fphoenixcorneae.jetpackmvvm.uistate

import com.fphoenixcorneae.jetpackmvvm.R
import com.kingja.loadsir.callback.Callback

/**
 * @desc：ErrorCallback
 * @date：2022/05/07 17:11
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int = R.layout.jm_layout_ui_state_error
}