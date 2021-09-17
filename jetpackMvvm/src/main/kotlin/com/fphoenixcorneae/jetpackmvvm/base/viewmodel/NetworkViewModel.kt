package com.fphoenixcorneae.jetpackmvvm.base.viewmodel

import com.fphoenixcorneae.jetpackmvvm.livedata.NetworkStateLiveData

/**
 * @desc：NetworkViewModel
 * @date：2021/09/15 17:30
 */
class NetworkViewModel : BaseViewModel() {
    val networkState = NetworkStateLiveData()
}