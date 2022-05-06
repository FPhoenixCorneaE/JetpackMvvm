package com.fphoenixcorneae.jetpackmvvm.base.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.common.dsl.layout.FrameLayout
import com.fphoenixcorneae.common.ext.dp
import com.fphoenixcorneae.common.ext.loge
import com.fphoenixcorneae.common.ext.toast
import com.fphoenixcorneae.common.ext.view.measureHeight
import com.fphoenixcorneae.jetpackmvvm.base.view.BaseView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.constant.JmConstants
import com.fphoenixcorneae.jetpackmvvm.lifecycle.LifecycleHandler
import com.fphoenixcorneae.jetpackmvvm.livedata.EventObserver
import com.fphoenixcorneae.jetpackmvvm.uistate.StatusLayoutManager
import com.fphoenixcorneae.toolbar.CommonToolbar

/**
 * @desc：Activity 基类
 * @date：2021/1/15 21:07
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), BaseView<VB> {

    companion object {

        // 开启这个 flag 后，你就可以正常使用 Selector 这样的 DrawableContainers 了。
        // 同时，你还开启了类似 android:drawableLeft 这样的 compound drawable 的使用权限，
        // 以及 RadioButton 的使用权限，以及 ImageView's src 属性。
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    /** 绑定生命周期的 Handler */
    private val mLifecycleHandler by lazy { LifecycleHandler(this) }

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 绑定视图 */
    private var viewBinding: VB? = null
    protected val mViewBinding get() = viewBinding!!

    /** 多状态布局管理器 */
    private var mStatusLayoutManager: StatusLayoutManager? = null

    /** 标题栏 */
    protected var mToolbar: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        viewBinding = initViewBinding().apply {
            (this as ViewDataBinding).lifecycleOwner = mContext
            setContentView(root)
        }
        initParams()
        initUiState()
        initView()
        initListener()
        initObserver()
        initData(intent.extras)
    }

    override fun setContentView(view: View?) {
        mToolbar = initToolbar()
        val contentView = FrameLayout {
            layoutParams = android.widget.FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // 添加内容
            addView(
                view,
                android.widget.FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).apply {
                    topMargin = contentViewMarginTop()
                }
            )
            mToolbar?.let { toolbar ->
                // 添加标题栏
                addView(toolbar)
            }
        }
        super.setContentView(contentView)
    }

    override fun contentViewMarginTop(): Int {
        return mToolbar?.measureHeight ?: 0
    }

    override fun initToolbar(): View? {
        return CommonToolbar(this).apply {
            layoutParams = JmConstants.Toolbar.LAYOUT_PARAMS
            leftType = JmConstants.Toolbar.LEFT_TYPE
            leftImageTint = JmConstants.Toolbar.LEFT_IMAGE_TINT_COLOR
            centerType = JmConstants.Toolbar.CENTER_TYPE
            centerTextColor = JmConstants.Toolbar.CENTER_TEXT_COLOR
            centerTextSize = JmConstants.Toolbar.CENTER_TEXT_SIZE.dp.toFloat()
            centerTextBold = JmConstants.Toolbar.CENTER_TEXT_IS_FAKE_BOLD
            showBottomLine = JmConstants.Toolbar.SHOW_BOTTOM_LINE
            toolbarHeight = JmConstants.Toolbar.TOOLBAR_HEIGHT
            toolbarColor = JmConstants.Toolbar.TOOLBAR_COLOR
            statusBarColor = JmConstants.Toolbar.STATUS_BAR_COLOR
            onToolbarClickListener = { v: View, action: Int, extra: CharSequence? ->
                if (action == CommonToolbar.TYPE_LEFT_IMAGE_BUTTON) {
                    onBackPressed()
                }
            }
        }
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