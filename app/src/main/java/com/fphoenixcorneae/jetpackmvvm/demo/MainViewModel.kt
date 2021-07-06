package com.fphoenixcorneae.jetpackmvvm.demo

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.ext.toastQQStyle
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.databinding.init
import com.fphoenixcorneae.util.ColorUtil

class MainViewModel : BaseViewModel() {

    val twoWayBindingText = MutableLiveData<String>()

    fun onClickImg(img: ImageView, imgUrl: Any?) {
        img.init(imgUrl, filterColor = ColorUtil.randomColor)
    }

    fun helloWorld(text: TextView) {
        toastQQStyle(text.text)
    }
}