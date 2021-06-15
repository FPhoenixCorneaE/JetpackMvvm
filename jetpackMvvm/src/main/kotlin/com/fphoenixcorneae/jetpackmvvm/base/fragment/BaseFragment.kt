package com.fphoenixcorneae.jetpackmvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.dsl.layout.FrameLayout
import com.fphoenixcorneae.ext.isNotNull
import com.fphoenixcorneae.ext.loge
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.jetpackmvvm.base.view.IView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.constant.JmConstants
import com.fphoenixcorneae.jetpackmvvm.lifecycle.FragmentLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.lifecycle.LifecycleHandler
import com.fphoenixcorneae.jetpackmvvm.livedata.EventObserver
import com.fphoenixcorneae.jetpackmvvm.network.NetworkState
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateManager
import com.fphoenixcorneae.jetpackmvvm.uistate.StatusLayoutManager
import com.fphoenixcorneae.toolbar.CommonToolbar
import com.fphoenixcorneae.util.ViewUtil

/**
 * @desc：Fragment 基类
 * @date：2021/1/15 21:07
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), IView<VB> {

    init {
        lifecycle.addObserver(FragmentLifecycleImpl())
    }

    /** 绑定生命周期的 Handler */
    private val mLifecycleHandler by lazy { LifecycleHandler(viewLifecycleOwner) }

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 视图是否加载完毕 */
    private var isViewPrepared = false

    /** 数据是否加载过了 */
    private var hasLoadedData = false

    /** 绑定视图 */
    private var viewBinding: VB? = null
    protected val mViewBinding get() = viewBinding!!

    /** 多状态布局管理器 */
    private var mStatusLayoutManager: StatusLayoutManager? = null

    /** 标题栏 */
    protected var mToolbar: CommonToolbar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // 加载布局
        return setContentView()
    }

    private fun setContentView(): View = kotlin.run {
        viewBinding = initViewBinding()
        initToolbar()
        FrameLayout {
            layoutParams = android.widget.FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // 添加内容
            addView(
                mViewBinding.root,
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
    }

    override fun contentViewMarginTop(): Int {
        if (mToolbar.isNotNull()) {
            return ViewUtil.getViewHeight(mToolbar!!)
        }
        return 0
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
        initParams()
        initUiState()
        initView()
        initListener()
        initObserver()
        lazyLoadDataIfPrepared()
    }

    override fun initToolbar(): View? {
        mToolbar = CommonToolbar(mContext).apply {
            layoutParams = JmConstants.Toolbar.LAYOUT_PARAMS
            centerType = JmConstants.Toolbar.CENTER_TYPE
            centerTextView?.apply {
                setTextColor(JmConstants.Toolbar.CENTER_TEXT_COLOR)
                textSize = JmConstants.Toolbar.CENTER_TEXT_SIZE
                paint.isFakeBoldText = JmConstants.Toolbar.CENTER_TEXT_IS_FAKE_BOLD
            }
            showBottomLine = JmConstants.Toolbar.SHOW_BOTTOM_LINE
            toolbarHeight = JmConstants.Toolbar.TOOLBAR_HEIGHT
            toolbarColor = JmConstants.Toolbar.TOOLBAR_COLOR
            statusBarColor = JmConstants.Toolbar.STATUS_BAR_COLOR
            // 不填充状态栏
            showStatusBar(false)
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

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepared && !hasLoadedData) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            mLifecycleHandler.postDelayed({
                view?.let {
                    initData(null)
                    // 在Fragment中，只有懒加载过了才能开启网络变化监听
                    addNetworkStateObserver()
                }
            }, lazyLoadTime())
            hasLoadedData = true
        }
    }

    protected fun addUiLoadingChangeObserver(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            // 显示弹窗
            viewModel.loadingChange.showDialog.observe(viewLifecycleOwner, EventObserver {
                showLoading(it)
            })
            // 关闭弹窗
            viewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, EventObserver {
                showContent()
            })
        }
    }

    private fun addNetworkStateObserver() {
        NetworkStateManager.networkState.observe(viewLifecycleOwner, EventObserver {
            // 视图加载完毕时调用方法，防止数据第一次监听错误
            if (hasLoadedData) {
                onNetworkStateChanged(it)
            }
        })
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
    open fun onNetworkStateChanged(it: NetworkState) {}

    override fun onDestroyView() {
        super.onDestroyView()
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