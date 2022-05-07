package com.fphoenixcorneae.jetpackmvvm.uistate

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.fphoenixcorneae.common.ext.view.load
import com.fphoenixcorneae.jetpackmvvm.R
import com.kingja.loadsir.core.LoadService

/**
 * 设置错误布局
 * @param errorMsg 错误布局显示的提示内容
 */
fun LoadService<*>.showError(
    errorImg: Any? = null,
    errorMsg: CharSequence? = null,
    retryText: CharSequence? = null
) {
    setErrorData(errorImg, errorMsg, retryText)
    showCallback(ErrorCallback::class.java)
}

fun LoadService<*>.setErrorData(
    errorImg: Any? = null,
    errorMsg: CharSequence? = null,
    retryText: CharSequence? = null
) {
    setCallBack(ErrorCallback::class.java) { _, view ->
        errorImg?.let {
            view.findViewById<ImageView>(R.id.ivError).apply {
                isVisible = true
                load(errorImg)
            }
        }
        if (errorMsg.isNullOrEmpty().not()) {
            view.findViewById<TextView>(R.id.tvError).text = errorMsg
        }
        if (retryText.isNullOrEmpty().not()) {
            view.findViewById<TextView>(R.id.tvRetry).text = retryText
        }
    }
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty(
    emptyImg: Any? = null,
    emptyMsg: CharSequence? = null,
    retryText: CharSequence? = null
) {
    setEmptyData(emptyImg, emptyMsg, retryText)
    showCallback(EmptyCallback::class.java)
}

fun LoadService<*>.setEmptyData(
    emptyImg: Any? = null,
    emptyMsg: CharSequence? = null,
    retryText: CharSequence? = null
) {
    setCallBack(ErrorCallback::class.java) { _, view ->
        emptyImg?.let {
            view.findViewById<ImageView>(R.id.ivEmpty).apply {
                isVisible = true
                load(emptyImg)
            }
        }
        if (emptyMsg.isNullOrEmpty().not()) {
            view.findViewById<TextView>(R.id.tvEmpty).text = emptyMsg
        }
        if (retryText.isNullOrEmpty().not()) {
            view.findViewById<TextView>(R.id.tvRetry).text = retryText
        }
    }
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading(
    loadingMsg: CharSequence? = null,
    showLoadingMsg: Boolean = true,
) {
    setLoadingData(loadingMsg, showLoadingMsg)
    showCallback(LoadingCallback::class.java)
}

fun LoadService<*>.setLoadingData(
    loadingMsg: CharSequence? = null,
    showLoadingMsg: Boolean = true,
) {
    setCallBack(LoadingCallback::class.java) { _, view ->
        view.findViewById<TextView>(R.id.tvLoading).apply {
            if (loadingMsg.isNullOrEmpty().not()) {
                text = loadingMsg
                isVisible = showLoadingMsg
            }
        }
    }
}

/**
 * 设置超时
 */
fun LoadService<*>.showNoNetwork(
    timeoutImg: Any? = null,
    timeoutMsg: CharSequence? = null,
    retryText: CharSequence? = null
) {
    setTimeoutData(timeoutImg, timeoutMsg, retryText)
    showCallback(NoNetworkCallback::class.java)
}

fun LoadService<*>.setTimeoutData(
    timeoutImg: Any? = null,
    timeoutMsg: CharSequence? = null,
    retryText: CharSequence? = null
) {
    setCallBack(NoNetworkCallback::class.java) { _, view ->
        timeoutImg?.let {
            view.findViewById<ImageView>(R.id.ivNoNetwork).apply {
                isVisible = true
                load(timeoutImg)
            }
        }
        if (timeoutMsg.isNullOrEmpty().not()) {
            view.findViewById<TextView>(R.id.tvNoNetwork).text = timeoutMsg
        }
        if (retryText.isNullOrEmpty().not()) {
            view.findViewById<TextView>(R.id.tvRetry).text = retryText
        }
    }
}
