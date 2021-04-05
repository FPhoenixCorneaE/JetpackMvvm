package com.fphoenixcorneae.core.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.dsl.layout.LinearLayout

/**
 * @desc：包含 ViewModel 和 DataBinding Fragment 基类，把 ViewModel 和 DataBinding 注入进来了，需要使用 DataBinding 的请继承它
 * @date：2021/4/5 20:01
 */
abstract class AbstractBaseVmDbFragment<VM : BaseViewModel, DB : ViewBinding> :
        AbstractBaseVmFragment<VM>() {

    private var dataBinding: DB? = null
    val mDataBinding get() = dataBinding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // 加载布局
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        setContentView()
        return mRootView
    }

    override fun setContentView() {
        mRootView = LinearLayout {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            )
            orientation = android.widget.LinearLayout.VERTICAL
            createToolbar()?.let { toolbar ->
                // 添加标题栏
                addView(toolbar)
            }
            // 添加内容
            addView(mDataBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding = null
    }
}