package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 Double 类型的 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:13
 */
class DoubleLiveData(var value: Double = 0.0) : MutableLiveData<Double>(value) {

    override fun getValue(): Double {
        return super.getValue() ?: 0.0
    }
}