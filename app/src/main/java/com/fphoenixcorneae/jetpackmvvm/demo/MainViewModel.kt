package com.fphoenixcorneae.jetpackmvvm.demo

import androidx.lifecycle.MutableLiveData
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    val twoWayBindingText1 = MutableLiveData<String>()
    val twoWayBindingText2 = MutableLiveData<String>()
}