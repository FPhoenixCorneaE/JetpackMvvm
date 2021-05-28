package com.fphoenixcorneae.jetpackmvvm.ext

import com.tencent.mmkv.MMKV

/**
 * MMKV 默认实例
 */
val defaultMMKV by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    MMKV.defaultMMKV()!!
}