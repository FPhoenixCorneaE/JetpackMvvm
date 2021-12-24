package com.fphoenixcorneae.jetpackmvvm.demo

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.ext.toastQQStyle
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.databinding.loadData
import com.fphoenixcorneae.util.ColorUtil

class MainViewModel : BaseViewModel() {

    val twoWayBindingText = MutableLiveData<String>()

    fun onClickImg(img: ImageView, imgData: Any?) {
        img.loadData(imgData, filterColor = ColorUtil.randomColor)
    }

    fun helloWorld(text: TextView) {
        toastQQStyle(text.text)
    }
}