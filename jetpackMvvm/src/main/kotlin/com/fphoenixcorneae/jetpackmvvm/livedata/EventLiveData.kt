package com.fphoenixcorneae.jetpackmvvm.livedata

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * @desc：发送消息 LiveData
 * 在 Activity 中 observe 调用 observeInActivity
 * 在 Fragment 中 observe 调用 observeInFragment
 * 具体写法请参考： https://github.com/KunMinX/UnPeek-LiveData的示例
 * @date：2021/04/11 19:05
 */
class EventLiveData<T> : UnPeekLiveData<T>()