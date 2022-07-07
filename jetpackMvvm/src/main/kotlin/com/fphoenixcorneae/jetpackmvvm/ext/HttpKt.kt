package com.fphoenixcorneae.jetpackmvvm.ext

import com.fphoenixcorneae.common.ext.loge
import com.fphoenixcorneae.coretrofit.exception.ApiException
import com.fphoenixcorneae.coretrofit.exception.Error
import com.fphoenixcorneae.coretrofit.exception.ExceptionHandler
import com.fphoenixcorneae.coretrofit.model.BaseResponse
import com.fphoenixcorneae.coretrofit.model.Result
import com.fphoenixcorneae.coretrofit.model.paresException
import com.fphoenixcorneae.coretrofit.model.paresResult
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.base.dialog.BaseDialog
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.livedata.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * http request 校验请求结果数据(过滤服务器结果)是否是成功
 * @param block        请求体方法
 * @param result       请求回调的[Result]数据
 * @param isShowDialog 是否显示加载框
 * @param loadingMsg   加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    result: MutableStateFlow<Result<T>?>,
    isShowDialog: Boolean = false,
    loadingMsg: String? = "正在努力加载中...",
): Job =
    launch(
        block = {
            if (isShowDialog) {
                result.value = Result.onLoading(loadingMsg = loadingMsg)
            }
            // 请求体
            block()
        },
        error = {
            it.message?.loge()
            result.paresException(it)
        }
    ) {
        result.paresResult(it)
    }


/**
 * http request 不校验请求结果数据是否是成功
 * @param block        请求体方法
 * @param result       请求回调的[Result]数据
 * @param isShowDialog 是否显示加载框
 * @param loadingMsg   加载框提示内容
 */
fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T,
    result: MutableStateFlow<Result<T>?>,
    isShowDialog: Boolean = false,
    loadingMsg: String? = "正在努力加载中...",
): Job =
    launch(
        block = {
            if (isShowDialog) {
                result.value = Result.onLoading(loadingMsg = loadingMsg)
            }
            // 请求体
            block()
        },
        error = {
            it.message?.loge()
            result.paresException(it)
        }
    ) {
        result.paresResult(it)
    }

/**
 * http request 校验请求结果数据(过滤服务器结果)是否是成功
 * @param block        请求体方法
 * @param success      成功回调
 * @param error        失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMsg   加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T?) -> Unit,
    error: (ApiException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMsg: String? = "正在努力加载中...",
): Job =
    launch(
        block = {
            if (isShowDialog) {
                loadingChange.showDialog.postValue(Event(loadingMsg))
            }
            // 请求体
            block()
        },
        error = { e ->
            // 网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(Event(true))
            // 打印错误消息
            e.message?.loge()
            // 失败回调
            error(ExceptionHandler.handleException(e))
        },
    ) {
        // 网络请求成功 关闭弹窗
        loadingChange.dismissDialog.postValue(Event(true))
        // 校验请求结果码是否正确
        if (it.isSuccess()) {
            success(it.getResponseData())
        } else {
            error(ApiException(errCode = it.getResponseCode(), error = it.getResponseMsg()))
        }
    }


/**
 * http request 校验请求结果数据(过滤服务器结果)是否是成功
 * @param block        请求体方法
 * @param success      成功回调
 * @param error        失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMsg   加载框提示内容
 */
fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T?,
    success: (T?) -> Unit,
    error: (ApiException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMsg: String? = "正在努力加载中...",
): Job =
    launch(
        block = {
            if (isShowDialog) {
                loadingChange.showDialog.postValue(Event(loadingMsg))
            }
            // 请求体
            block()
        },
        error = { e ->
            // 网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(Event(true))
            // 打印错误消息
            e.message?.loge()
            // 失败回调
            error(ExceptionHandler.handleException(e))
        }
    ) {
        // 网络请求成功 关闭弹窗
        loadingChange.dismissDialog.postValue(Event(true))
        // 成功回调
        success(it)
    }

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param onSuccess 成功回调
 * @param onError   失败回调
 * @param onLoading 加载中
 */
fun <T> Result<T>?.parseResult(
    activity: BaseActivity<*>,
    onSuccess: (T?) -> Unit,
    onError: ((ApiException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
) {
    if (this == null) {
        return
    }
    when (this) {
        is Result.Loading -> {
            activity.showLoading(loadingMsg)
            onLoading?.run { this }
        }
        is Result.Success -> {
            if (data == null) {
                activity.showEmpty(null)
            } else {
                activity.showContent()
            }
            onSuccess(data)
        }
        is Result.Error -> {
            if (exception.isNoNetwork()) {
                activity.showNoNetwork(noNetworkMsg = exception.errorMsg)
            } else {
                activity.showError(exception.errorMsg)
            }
            onError?.run { this(exception) }
        }
        else -> {}
    }
}

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param onSuccess 成功回调
 * @param onError 失败回调
 * @param onLoading 加载中
 */
fun <T> Result<T>?.parseResult(
    fragment: BaseFragment<*>,
    onSuccess: (T?) -> Unit,
    onError: ((ApiException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
) {
    if (this == null) {
        return
    }
    when (this) {
        is Result.Loading -> {
            fragment.showLoading(loadingMsg)
            onLoading?.run { this }
        }
        is Result.Success -> {
            if (data == null) {
                fragment.showEmpty(null)
            } else {
                fragment.showContent()
            }
            onSuccess(data)
        }
        is Result.Error -> {
            if (exception.isNoNetwork()) {
                fragment.showNoNetwork(noNetworkMsg = exception.errorMsg)
            } else {
                fragment.showError(exception.errorMsg)
            }
            onError?.run { this(exception) }
        }
        else -> {}
    }
}

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param onSuccess 成功回调
 * @param onError 失败回调
 * @param onLoading 加载中
 */
fun <T> Result<T>?.parseResult(
    dialog: BaseDialog<*>,
    onSuccess: (T?) -> Unit,
    onError: ((ApiException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
) {
    if (this == null) {
        return
    }
    when (this) {
        is Result.Loading -> {
            dialog.showLoading(loadingMsg)
            onLoading?.run { this }
        }
        is Result.Success -> {
            if (data == null) {
                dialog.showEmpty(null)
            } else {
                dialog.showContent()
            }
            onSuccess(data)
        }
        is Result.Error -> {
            if (exception.isNoNetwork()) {
                dialog.showNoNetwork(noNetworkMsg = exception.errorMsg)
            } else {
                dialog.showError(exception.errorMsg)
            }
            onError?.run { this(exception) }
        }
        else -> {}
    }
}

fun ApiException.isNoNetwork() = run {
    when (errorCode) {
        Error.HTTP_UNAUTHORIZED.getCode(),
        Error.HTTP_FORBIDDEN.getCode(),
        Error.HTTP_NOT_FOUND.getCode(),
        Error.HTTP_REQUEST_TIMEOUT.getCode(),
        Error.HTTP_INTERNAL_SERVER_ERROR.getCode(),
        Error.HTTP_BAD_GATEWAY.getCode(),
        Error.HTTP_SERVICE_UNAVAILABLE.getCode(),
        Error.HTTP_GATEWAY_TIMEOUT.getCode(),
        Error.NETWORK_ERROR.getCode(),
        Error.TIMEOUT_ERROR.getCode(),
        -> true
        else -> false
    }
}