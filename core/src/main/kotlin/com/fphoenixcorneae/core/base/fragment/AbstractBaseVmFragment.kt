package com.fphoenixcorneae.core.base.fragment

import android.os.Bundle
import android.view.View
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.core.network.NetWorkState
import com.fphoenixcorneae.core.network.NetworkStateManager
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

/**
 * @desc：ViewModelFragment 基类，自动把 ViewModel 注入 Fragment
 * @date：2021/4/5 20:01
 */
abstract class AbstractBaseVmFragment<VM : BaseViewModel> : AbstractBaseFragment() {

    lateinit var mViewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addUILoadingChangeObserver()
        addNetworkStateObserver()
    }

    private fun addUILoadingChangeObserver() {
        // 显示弹窗
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, {
            showLoading(
                    if (it.isEmpty()) {
                        "请求网络中..."
                    } else {
                        it
                    }
            )
        })
        // 关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, {
            showContent()
        })
    }

    private fun addNetworkStateObserver() {
        NetworkStateManager.networkState.observe(viewLifecycleOwner, {
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