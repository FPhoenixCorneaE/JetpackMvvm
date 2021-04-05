package com.fphoenixcorneae.core.base.activity

import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel

/**
 * @desc：包含 ViewModel 和 DataBinding Activity 基类，把 ViewModel 和 DataBinding 注入进来了，需要使用 DataBinding 的请继承它
 * @date：2021/4/4 13:52
 */
abstract class AbstractBaseVmDbActivity<VM : BaseViewModel, DB : ViewBinding> :
        AbstractBaseVmActivity<VM>() {
    private var dataBinding: DB? = null
    val mDataBinding get() = dataBinding!!

    override fun setContentView() {
        dataBinding = DataBindingUtil.setContentView(mContext, getLayoutId())
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding = null
    }
}
