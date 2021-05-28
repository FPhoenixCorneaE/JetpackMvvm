package com.fphoenixcorneae.jetpackmvvm.base.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.dsl.layout.LinearLayout
import com.fphoenixcorneae.ext.loge
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.ext.view.setTintColor
import com.fphoenixcorneae.jetpackmvvm.base.view.IView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.constant.JmConstants
import com.fphoenixcorneae.jetpackmvvm.livedata.EventObserver
import com.fphoenixcorneae.jetpackmvvm.network.NetworkState
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateManager
import com.fphoenixcorneae.jetpackmvvm.uistate.StatusLayoutManager
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
    private var mStatusLayoutManager: StatusLayoutManager? = null

    /** 标题栏 */
    protected var mToolbar: CommonTitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        viewBinding = initViewBinding().apply {
            setContentView(root)
        }
        initParams()
        initUiState()
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
            initToolbar()?.let { toolbar ->
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

    override fun initToolbar(): View? {
        mToolbar = CommonTitleBar(this).apply {
            layoutParams = JmConstants.Toolbar.LAYOUT_PARAMS
            leftType = JmConstants.Toolbar.LEFT_TYPE
            leftImageButton?.setTintColor(JmConstants.Toolbar.LEFT_IMAGE_TINT_COLOR)
            centerType = JmConstants.Toolbar.CENTER_TYPE
            centerTextView?.apply {
                setTextColor(JmConstants.Toolbar.CENTER_TEXT_COLOR)
                textSize = JmConstants.Toolbar.CENTER_TEXT_SIZE
                paint.isFakeBoldText = JmConstants.Toolbar.CENTER_TEXT_IS_FAKE_BOLD
            }
            showBottomLine = JmConstants.Toolbar.SHOW_BOTTOM_LINE
            titleBarHeight = JmConstants.Toolbar.TITLE_BAR_HEIGHT
            titleBarColor = JmConstants.Toolbar.TITLE_BAR_COLOR
            statusBarColor = JmConstants.Toolbar.STATUS_BAR_COLOR
        }
        return mToolbar
    }

    override fun initUiState() {
        mStatusLayoutManager = StatusLayoutManager.Builder()
            .addNoNetWorkClickListener {
                onNoNetWorkClick()
            }
            .addErrorClickListener {
                onErrorClick()
            }
            .addEmptyClickListener {
                onEmptyClick()
            }
            .register(viewBinding!!.root)
    }

    protected fun addUiLoadingChangeObserver(vararg viewModels: BaseViewModel) {
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
    open fun onNetworkStateChanged(it: NetworkState) {}

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun showLoading(loadingMsg: String?) {
        mStatusLayoutManager?.showLoadingView(loadingMsg)
    }

    override fun showContent() {
        mStatusLayoutManager?.showContentView()
    }

    override fun showEmpty(emptyMsg: String?) {
        mStatusLayoutManager?.showEmptyView(emptyMsg)
    }

    override fun showNoNetwork(noNetworkMsg: String?) {
        mStatusLayoutManager?.showNoNetWorkView(noNetworkMsg)
    }

    override fun showError(errorMsg: String?) {
        mStatusLayoutManager?.showErrorView(errorMsg)
    }

    override fun toastErrorMsg(errorMsg: CharSequence?, t: Throwable?) {
        toast(errorMsg)
        t.toString().loge()
    }

    override fun onNoNetWorkClick() {
        showLoading(null)
        initData(null)
    }

    override fun onErrorClick() {
        showLoading(null)
        initData(null)
    }

    override fun onEmptyClick() {
        showLoading(null)
        initData(null)
    }
}