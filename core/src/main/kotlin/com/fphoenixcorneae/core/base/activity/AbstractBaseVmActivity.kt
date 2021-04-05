package com.fphoenixcorneae.core.base.activity

import android.os.Bundle
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.core.network.NetWorkState
import com.fphoenixcorneae.core.network.NetworkStateManager
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

/**
 * @desc：ViewModelActivity 基类，把 ViewModel 注入进来
 * @date：2021/4/4 13:52
 */
abstract class AbstractBaseVmActivity<VM : BaseViewModel> : AbstractBaseActivity() {
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addUILoadingChangeObserver()
        addNetworkStateObserver()
    }

    private fun addUILoadingChangeObserver() {
        // 显示弹窗
        mViewModel.loadingChange.showDialog.observe(this, {
            showLoading(
                if (it.isEmpty()) {
                    "请求网络中..."
                } else {
                    it
                }
            )
        })
        // 关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this, {
            showContent()
        })
    }

    private fun addNetworkStateObserver() {
        NetworkStateManager.networkState.observe(this, {
            onNetworkStateChanged(it)
        })
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(it: NetWorkState) {}

    override fun initViewModel() {
        val clazz =
            this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<VM>
        // koin 注入
        mViewModel = getViewModel(clazz = clazz)
    }
}