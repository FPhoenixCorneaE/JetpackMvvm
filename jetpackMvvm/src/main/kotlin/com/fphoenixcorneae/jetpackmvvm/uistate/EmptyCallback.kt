package com.fphoenixcorneae.jetpackmvvm.uistate

import com.fphoenixcorneae.jetpackmvvm.R
import com.kingja.loadsir.callback.Callback

/**
 * @desc：EmptyCallback
 * @date：2022/05/07 17:09
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int = R.layout.jm_layout_ui_state_empty
}