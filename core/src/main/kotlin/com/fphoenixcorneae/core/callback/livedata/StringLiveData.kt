package com.fphoenixcorneae.core.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @desc：自定义的 String 类型的 MutableLiveData  提供了默认值，避免取值的时候还要判空
 * @date：2021/4/5 19:15
 */
class StringLiveData(value: String = "") : MutableLiveData<String>(value) {

    override fun getValue(): String {
        return super.getValue()!!
    }
}