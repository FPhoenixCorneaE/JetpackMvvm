package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 Byte 类型的 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:12
 */
class ByteLiveData(value: Byte = 0) : MutableLiveData<Byte>(value) {

    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}