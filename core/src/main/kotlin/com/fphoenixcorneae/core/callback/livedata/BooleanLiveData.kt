package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 Boolean 类型的 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:04
 */
class BooleanLiveData(value: Boolean = false) : MutableLiveData<Boolean>(value) {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}