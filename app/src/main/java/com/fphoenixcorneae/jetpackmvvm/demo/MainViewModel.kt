package com.fphoenixcorneae.jetpackmvvm.demo

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.common.ext.getRandomColor
import com.fphoenixcorneae.common.ext.toastQQStyle
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.databinding.loadData

class MainViewModel : BaseViewModel() {

    val twoWayBindingText = MutableLiveData<String>()

    fun onClickImg(img: ImageView, imgData: Any?) {
        img.loadData(imgData, filterColor = getRandomColor())
    }

    fun helloWorld(text: TextView) {
        toastQQStyle(text.text)
    }
}