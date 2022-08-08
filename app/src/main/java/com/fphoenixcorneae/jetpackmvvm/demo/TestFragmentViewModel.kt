package com.fphoenixcorneae.jetpackmvvm.demo

import androidx.lifecycle.viewModelScope
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @desc：TestFragmentViewModel
 * @date：2022/08/05 17:50
 */
class TestFragmentViewModel : BaseViewModel() {

    private val _onBackPressed4 = MutableStateFlow<Boolean?>(null)
    val onBackPressed4 = _onBackPressed4.asStateFlow()
    private val _onBackPressed3 = MutableStateFlow<Boolean?>(null)
    val onBackPressed3 = _onBackPressed3.asStateFlow()
    private val _onBackPressed2 = MutableStateFlow<Boolean?>(null)
    val onBackPressed2 = _onBackPressed2.asStateFlow()
    private val _onBackPressed1 = MutableStateFlow<Boolean?>(null)
    val onBackPressed1 = _onBackPressed1.asStateFlow()

    fun setOnBackPressed4Click() {
        viewModelScope.launch {
            _onBackPressed4.emit(true)
        }
        viewModelScope.launch {
            _onBackPressed4.emit(null)
        }
    }

    fun setOnBackPressed3Click() {
        viewModelScope.launch {
            _onBackPressed3.emit(true)
        }
        viewModelScope.launch {
            _onBackPressed3.emit(null)
        }
    }

    fun setOnBackPressed2Click() {
        viewModelScope.launch {
            _onBackPressed2.emit(true)
        }
        viewModelScope.launch {
            _onBackPressed2.emit(null)
        }
    }

    fun setOnBackPressed1Click() {
        viewModelScope.launch {
            _onBackPressed1.emit(true)
        }
        viewModelScope.launch {
            _onBackPressed1.emit(null)
        }
    }
}