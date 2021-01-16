package com.fphoenixcorneae.core.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * @desc：视图基类 接口
 * @date：2021/1/15 17:54
 */
interface IBaseView {

    /**
     * 获取内容布局id
     */
    @LayoutRes
    fun getLayoutId(): Int

    /**
     * 构造多状态布局管理器
     */
    fun createMultiStatusLayoutManager() {}

    /**
     * 初始化界面传递参数
     */
    fun initParam() {}

    /**
     * 初始化View
     */
    fun initView() {}

    /**
     * 初始化监听器
     */
    fun initListener() {}

    /**
     * 初始化界面观察者的监听
     */
    fun initViewObservable() {}

    /**
     * 初始化数据
     */
    fun initData(savedInstanceState: Bundle?) {}

    /**
     * 显示加载中视图
     */
    fun showLoading() {}

    /**
     * 显示数据内容视图
     */
    fun showContent() {}

    /**
     * 显示空数据视图
     */
    fun showEmpty() {}

    /**
     * 显示无网络视图
     */
    fun showNoNetwork() {}

    /**
     * 显示错误视图
     */
    fun showError() {}

    /**
     * 显示错误信息
     * @param t 异常
     */
    fun showErrorMsg(t: Throwable) {}

    /**
     * 显示错误信息
     * @param errorMsg 错误信息
     */
    fun showErrorMsg(errorMsg: CharSequence) {}

    /**
     * 无网络视图点击
     */
    fun onNoNetWorkClick() {}

    /**
     * 错误视图点击
     */
    fun onErrorClick() {}

    /**
     * 无数据视图点击
     */
    fun onEmptyClick() {}
}