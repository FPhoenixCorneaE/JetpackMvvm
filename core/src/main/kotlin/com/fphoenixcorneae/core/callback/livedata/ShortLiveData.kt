package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 Short 类型的 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:14
 */
class ShortLiveData(value: Short = 0) : MutableLiveData<Short>(value) {

    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}