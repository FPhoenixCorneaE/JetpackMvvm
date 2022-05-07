package com.fphoenixcorneae.jetpackmvvm.uistate

import android.content.Context
import android.view.View
import com.fphoenixcorneae.jetpackmvvm.R
import com.kingja.loadsir.callback.Callback

/**
 * @desc：NoNetworkCallback
 * @date：2022/05/07 17:14
 */
class NoNetworkCallback : Callback() {
    override fun onCreateView(): Int = R.layout.jm_layout_ui_state_no_network

    override fun onReloadEvent(context: Context?, view: View?): Boolean = false
}