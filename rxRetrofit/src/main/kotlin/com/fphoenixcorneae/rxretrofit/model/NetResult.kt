package com.fphoenixcorneae.rxretrofit.model

import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.rxretrofit.exception.ApiException
import com.fphoenixcorneae.rxretrofit.exception.ExceptionHandle

/**
 * @desc：自定义结果集封装类
 * @date：2021/4/4 15:21
 */
sealed class NetResult<out T> {
    companion object {
        fun <T> onSuccess(data: T): NetResult<T> = Success(data)
        fun <T> onLoading(loadingMessage: String): NetResult<T> = Loading(loadingMessage)
        fun <T> onError(exception: ApiException): NetResult<T> = Error(exception)
    }

    data class Success<out T>(val data: T) : NetResult<T>()
    data class Loading(val loadingMessage: String) : NetResult<Nothing>()
    data class Error(val exception: ApiException) : NetResult<Nothing>()
}


/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T> MutableLiveData<NetResult<T>>.paresResult(result: AbstractBaseResponse<T>) {
    value = if (result.isSuccess()) {
        NetResult.onSuccess(result.getResponseData())
    } else {
        NetResult.onError(ApiException(result.getResponseCode(), result.getResponseMsg()))
    }
}

/**
 * 不处理返回值 直接返回请求结果
 * @param result 请求结果
 */
fun <T> MutableLiveData<NetResult<T>>.paresResult(result: T) {
    value = NetResult.onSuccess(result)
}

/**
 * 异常转换异常处理
 */
fun <T> MutableLiveData<NetResult<T>>.paresException(e: Throwable) {
    this.value = NetResult.onError(ExceptionHandle.handleException(e))
}

