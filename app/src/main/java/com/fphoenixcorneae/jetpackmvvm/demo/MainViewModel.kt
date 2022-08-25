package com.fphoenixcorneae.jetpackmvvm.demo

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.common.ext.getRandomColor
import com.fphoenixcorneae.common.ext.logd
import com.fphoenixcorneae.common.ext.toastAliPayStyle
import com.fphoenixcorneae.common.ext.toastQQStyle
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.databinding.loadData
import com.fphoenixcorneae.jetpackmvvm.ext.launch
import com.fphoenixcorneae.jetpackmvvm.ext.launchIO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class MainViewModel : BaseViewModel() {

    val twoWayBindingText = MutableLiveData<String>()

    private val _timeCountDown = MutableStateFlow(5)
    val timeCountDown = _timeCountDown.asStateFlow()

    val repeatOnLifecycleFlow = flow { emit("launchRepeatOnLifecycle") }

    fun onClickImg(img: ImageView, imgData: Any?) {
        img.loadData(imgData, filterColor = getRandomColor())

        launch(
            block = {
                IntArray(1000) { it * it }.toList() to System.currentTimeMillis()
            },
            error = {
            }
        ) {
            "sum：${it.first.sum()}  耗时：${System.currentTimeMillis() - it.second}".logd("launch")
        }
        launchIO(
            block = {
                LongRange(1, 1_000_000).step(1).toList() to System.currentTimeMillis()
            },
            error = {
            }
        ) {
            "sum：${it.first.sum()}  耗时：${System.currentTimeMillis() - it.second}".logd("launchIO")
        }

        launch(block = {
            repeat(6) {
                _timeCountDown.emit(5 - it)
                delay(1000)
            }
        })
    }

    fun onMultiClickImg() {
        toastAliPayStyle("onMultiClickImg")
        "onMultiClickImg".logd("onMultiClickImg")
    }

    fun helloWorld(text: TextView) {
        toastQQStyle(text.text)
    }
}