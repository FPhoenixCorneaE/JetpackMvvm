package com.fphoenixcorneae.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.CoreConstants
import com.fphoenixcorneae.core.base.view.IBaseView
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.core.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.core.network.NetWorkState
import com.fphoenixcorneae.core.network.NetworkStateManager
import com.fphoenixcorneae.dsl.layout.LinearLayout
import com.fphoenixcorneae.ext.loggerE
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.ext.view.setTintColor
import com.fphoenixcorneae.titlebar.CommonTitleBar
import com.fphoenixcorneae.util.ContextUtil

/**
 * @desc：Fragment 基类
 * @date：2021/1/15 21:07
 */
abstract class AbstractBaseFragment<VB : ViewBinding>(@LayoutRes val layoutResID: Int) : Fragment(layoutResID), IBaseView<VB> {

    /** 视图是否加载完毕 */
    protected var isViewPrepared = false

    /** 数据是否加载过了 */
    protected var hasLoadedData = false

    /** Fragment 根视图 */
    protected var mRootView: View? = null

    /** 绑定视图 */
    private var viewBinding: ViewBinding? = null
    protected val mViewBinding get() = viewBinding!! as VB

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 多状态布局管理器 */
    private var mMultiStatusLayoutManager: MultiStatusLayoutManager? = null

    /** 标题栏 */
    protected var mToolbar: CommonTitleBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = requireActivity()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // 加载布局
        setContentView()
        return mRootView
    }

    private fun setContentView() {
        viewBinding = initViewBinding()
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
            if (viewBinding == null) {
                layoutInflater.inflate(layoutResID, this, false)?.let { content ->
                    addView(content)
                }
            } else {
                addView(mViewBinding.root, android.widget.LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ))
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        createMultiStatusLayoutManager()
        initToolbar()
        initParam()
        initView()
        initListener()
        initViewObservable()
        lazyLoadDataIfPrepared()
    }

    override fun createToolbar(): View? {
        mToolbar = CommonTitleBar(mContext).apply {
            layoutParams = CoreConstants.Toolbar.LAYOUT_PARAMS
            leftType = CoreConstants.Toolbar.LEFT_TYPE
            leftImageButton?.setTintColor(CoreConstants.Toolbar.LEFT_IMAGE_TINT_COLOR)
            centerType = CoreConstants.Toolbar.CENTER_TYPE
            centerTextView?.apply {
                setTextColor(CoreConstants.Toolbar.CENTER_TEXT_COLOR)
                textSize = CoreConstants.Toolbar.CENTER_TEXT_SIZE
                paint.isFakeBoldText = CoreConstants.Toolbar.CENTER_TEXT_IS_FAKE_BOLD
            }
            showBottomLine = CoreConstants.Toolbar.SHOW_BOTTOM_LINE
            titleBarHeight = CoreConstants.Toolbar.TITLE_BAR_HEIGHT
            titleBarColor = CoreConstants.Toolbar.TITLE_BAR_COLOR
            statusBarColor = CoreConstants.Toolbar.STATUS_BAR_COLOR
            // 不填充状态栏
            showStatusBar(false)
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

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepared && !hasLoadedData) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            ContextUtil.runOnUiThreadDelayed({
                initData(null)
                // 在Fragment中，只有懒加载过了才能开启网络变化监听
                addNetworkStateObserver()
            }, lazyLoadTime())
            hasLoadedData = true
        }
    }

    protected fun addUILoadingChangeObserver(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            // 显示弹窗
            viewModel.loadingChange.showDialog.observeInFragment(this) {
                showLoading(it)
            }
            // 关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInFragment(this) {
                showContent()
            }
        }
    }

    private fun addNetworkStateObserver() {
        NetworkStateManager.networkState.observeInFragment(this) {
            // 视图加载完毕时调用方法，防止数据第一次监听错误
            if (hasLoadedData) {
                onNetworkStateChanged(it)
            }
        }
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(it: NetWorkState) {}

    override fun onDestroyView() {
        super.onDestroyView()
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