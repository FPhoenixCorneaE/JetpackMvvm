package com.fphoenixcorneae.jetpackmvvm.base.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * @desc：视图接口
 * @date：2021/1/15 17:54
 */
interface BaseView<VB : ViewDataBinding> {

    /**
     * 初始化视图绑定
     */
    fun VB.initViewBinding()

    /**
     * 初始化界面传递参数
     */
    fun initParams() {}

    /**
     * 构造状态布局管理器
     */
    fun initUiState() {}

    /**
     * 初始化标题栏
     */
    fun initToolbar(): View? {
        return null
    }

    /**
     * 内容视图顶部边距
     */
    fun contentViewMarginTop(): Int = 0

    /**
     * 初始化View
     */
    fun VB.initView() {}

    /**
     * 初始化监听器
     */
    fun VB.initListener() {}

    /**
     * 初始化界面观察者的监听
     */
    fun VB.initObserver() {}

    /**
     * 初始化数据
     */
    fun initData(savedInstanceState: Bundle?) {}

    /**
     * 显示加载中视图
     */
    fun showLoading(loadingMsg: String?) {}

    /**
     * 显示数据内容视图
     */
    fun showContent() {}

    /**
     * 显示空数据视图
     */
    fun showEmpty(emptyMsg: String?) {}

    /**
     * 显示无网络视图
     */
    fun showNoNetwork(noNetworkMsg: String?) {}

    /**
     * 显示错误视图
     */
    fun showError(errorMsg: String?) {}

    /**
     * 吐司错误信息
     * @param errorMsg 错误信息
     * @param t        异常
     */
    fun toastErrorMsg(errorMsg: CharSequence?, t: Throwable?) {}
}