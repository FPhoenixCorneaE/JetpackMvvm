package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 Float 类型 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:13
 */
class FloatLiveData(value: Float = 0f) : MutableLiveData<Float>(value) {

    override fun getValue(): Float {
        return super.getValue() ?: 0f
    }
}