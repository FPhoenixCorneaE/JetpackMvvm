package com.fphoenixcorneae.jetpackmvvm.base.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.jetpackmvvm.JMConstants
import com.fphoenixcorneae.jetpackmvvm.base.view.IView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.jetpackmvvm.network.NetWorkState
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateManager
import com.fphoenixcorneae.dsl.layout.LinearLayout
import com.fphoenixcorneae.ext.loggerE
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.ext.view.setTintColor
import com.fphoenixcorneae.jetpackmvvm.livedata.EventObserver
import com.fphoenixcorneae.titlebar.CommonTitleBar

/**
 * @desc：Activity 基类
 * @date：2021/1/15 21:07
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IView<VB> {

    companion object {

        // 开启这个 flag 后，你就可以正常使用 Selector 这样的 DrawableContainers 了。
        // 同时，你还开启了类似 android:drawableLeft 这样的 compound drawable 的使用权限，
        // 以及 RadioButton 的使用权限，以及 ImageView's src 属性。
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 绑定视图 */
    private var viewBinding: VB? = null
    protected val mViewBinding get() = viewBinding!!

    /** 多状态布局管理器 */
    private var mMultiStatusLayoutManager: MultiStatusLayoutManager? = null

    /** 标题栏 */
    protected var mToolbar: CommonTitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        viewBinding = initViewBinding().apply {
            setContentView(root)
        }
        initParams()
        createMultiStatusLayoutManager()
        initToolbar()
        initView()
        initListener()
        initViewObservable()
        addNetworkStateObserver()
        initData(savedInstanceState)
    }

    override fun setContentView(view: View?) {
        val contentView = LinearLayout {
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
            addView(
                view,
                android.widget.LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        super.setContentView(contentView)
    }

    override fun createToolbar(): View? {
        mToolbar = CommonTitleBar(this).apply {
            layoutParams = JMConstants.Toolbar.LAYOUT_PARAMS
            leftType = JMConstants.Toolbar.LEFT_TYPE
            leftImageButton?.setTintColor(JMConstants.Toolbar.LEFT_IMAGE_TINT_COLOR)
            centerType = JMConstants.Toolbar.CENTER_TYPE
            centerTextView?.apply {
                setTextColor(JMConstants.Toolbar.CENTER_TEXT_COLOR)
                textSize = JMConstants.Toolbar.CENTER_TEXT_SIZE
                paint.isFakeBoldText = JMConstants.Toolbar.CENTER_TEXT_IS_FAKE_BOLD
            }
            showBottomLine = JMConstants.Toolbar.SHOW_BOTTOM_LINE
            titleBarHeight = JMConstants.Toolbar.TITLE_BAR_HEIGHT
            titleBarColor = JMConstants.Toolbar.TITLE_BAR_COLOR
            statusBarColor = JMConstants.Toolbar.STATUS_BAR_COLOR
        }
        return mToolbar
    }

    override fun createMultiStatusLayoutManager() {
        mMultiStatusLayoutManager = MultiStatusLayoutManager.Builder()
            .addNoNetWorkClickListener {
                onNoNetWorkClick()
            }
            .addErrorClickListener {
                onErrorClick()
            }
            .addEmptyClickListener {
                onEmptyClick()
            }
            .register(this)
    }

    protected fun addUILoadingChangeObserver(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            // 显示弹窗
            viewModel.loadingChange.showDialog.observe(this, EventObserver {
                showLoading(it)
            })
            // 关闭弹窗
            viewModel.loadingChange.dismissDialog.observe(this, EventObserver {
                showContent()
            })
        }
    }

    private fun addNetworkStateObserver() {
        NetworkStateManager.networkState.observe(this, EventObserver {
            onNetworkStateChanged(it)
        })
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(it: NetWorkState) {}

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun showLoading(loadingMsg: String) {
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
        showLoading("")
        initData(null)
    }

    override fun onErrorClick() {
        showLoading("")
        initData(null)
    }

    override fun onEmptyClick() {
        showLoading("")
        initData(null)
    }
}