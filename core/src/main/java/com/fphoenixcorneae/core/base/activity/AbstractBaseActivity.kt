package com.fphoenixcorneae.core.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import com.fphoenixcorneae.core.base.view.IBaseView
import com.fphoenixcorneae.core.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.ext.loggerE
import com.fphoenixcorneae.ext.toast

/**
 * @desc：Activity 基类
 * @date：2021/1/15 21:07
 */
abstract class AbstractBaseActivity : AppCompatActivity(), IBaseView {

    companion object {

        // 开启这个 flag 后，你就可以正常使用 Selector 这样的 DrawableContainers 了。
        // 同时，你还开启了类似 android:drawableLeft 这样的 compound drawable 的使用权限，
        // 以及 RadioButton 的使用权限，以及 ImageView’s src 属性。
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 多状态布局管理器 */
    private var mMultiStatusLayoutManager: MultiStatusLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(getLayoutId())
        createMultiStatusLayoutManager()
        initParam()
        initView()
        initListener()
        initViewObservable()
        initData(savedInstanceState)
    }

    override fun createMultiStatusLayoutManager() {
        mMultiStatusLayoutManager = MultiStatusLayoutManager.Builder()
            .addNoNetWorkClickListener(View.OnClickListener {
                onNoNetWorkClick()
            })
            .addErrorClickListener(View.OnClickListener {
                onErrorClick()
            })
            .addEmptyClickListener(View.OnClickListener {
                onEmptyClick()
            })
            .register(this)
    }

    override fun showLoading() {
        mMultiStatusLayoutManager?.showLoadingView()
    }

    override fun showContent() {
        mMultiStatusLayoutManager?.showContentView()
    }

    override fun showEmpty() {
        mMultiStatusLayoutManager?.showEmptyView()
    }

    override fun showNoNetwork() {
        mMultiStatusLayoutManager?.showNoNetWorkView()
    }

    override fun showError() {
        mMultiStatusLayoutManager?.showErrorView()
    }

    override fun showErrorMsg(t: Throwable) {
        loggerE(t.toString())
    }

    override fun showErrorMsg(errorMsg: CharSequence) {
        toast(errorMsg)
    }

    override fun onNoNetWorkClick() {
        showLoading()
        initData(null)
    }

    override fun onErrorClick() {
        showLoading()
        initData(null)
    }

    override fun onEmptyClick() {
        showLoading()
        initData(null)
    }
}