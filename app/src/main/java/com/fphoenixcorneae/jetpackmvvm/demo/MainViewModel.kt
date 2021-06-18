package com.fphoenixcorneae.jetpackmvvm.demo

import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    val twoWayBindingText = MutableLiveData<String>()
}