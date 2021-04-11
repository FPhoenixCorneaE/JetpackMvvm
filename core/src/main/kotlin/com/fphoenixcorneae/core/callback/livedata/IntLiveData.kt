package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 Int 类型的 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:14
 */
class IntLiveData(value: Int = 0) : MutableLiveData<Int>(value) {

    override fun getValue(): Int {
        return super.getValue() ?: 0
    }
}