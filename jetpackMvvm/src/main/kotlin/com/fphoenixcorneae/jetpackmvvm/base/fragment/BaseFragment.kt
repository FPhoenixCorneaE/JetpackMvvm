package com.fphoenixcorneae.jetpackmvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.fphoenixcorneae.common.dsl.layout.FrameLayout
import com.fphoenixcorneae.common.ext.dp
import com.fphoenixcorneae.common.ext.loge
import com.fphoenixcorneae.common.ext.toast
import com.fphoenixcorneae.common.ext.view.measureHeight
import com.fphoenixcorneae.jetpackmvvm.base.view.BaseView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.constant.JmConstants
import com.fphoenixcorneae.jetpackmvvm.ext.getViewBinding
import com.fphoenixcorneae.jetpackmvvm.lifecycle.FragmentLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.lifecycle.LifecycleHandler
import com.fphoenixcorneae.jetpackmvvm.livedata.EventObserver
import com.fphoenixcorneae.jetpackmvvm.uistate.showEmpty
import com.fphoenixcorneae.jetpackmvvm.uistate.showError
import com.fphoenixcorneae.jetpackmvvm.uistate.showLoading
import com.fphoenixcorneae.jetpackmvvm.uistate.showNoNetwork
import com.fphoenixcorneae.toolbar.CommonToolbar
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlin.properties.Delegates

/**
 * @desc：Fragment 基类
 * @date：2021/1/15 21:07
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment(), BaseView<VB>, Callback.OnReloadListener {

    init {
        lifecycle.addObserver(FragmentLifecycleImpl())
    }

    /** 视图是否加载完毕 */
    private var isViewPrepared = false

    /** 数据是否加载过了 */
    private var hasLoadedData = false

    /** 绑定视图 */
    private var viewBinding: VB? = null

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** ViewBinding */
    protected val mViewBinding get() = viewBinding!!

    /** 绑定生命周期的 Handler */
    protected val mLifecycleHandler by lazy { LifecycleHandler(viewLifecycleOwner) }

    /** 多状态布局管理服务 */
    protected var mLoadService by Delegates.notNull<LoadService<*>>()

    /** 标题栏 */
    protected var mToolbar: View? = null

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
        return setContentView(inflater, container)
    }

    private fun setContentView(inflater: LayoutInflater, container: ViewGroup?): View = run {
        viewBinding = getViewBinding<VB>(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        mToolbar = initToolbar()
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
        return mToolbar?.measureHeight ?: 0
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
        viewBinding?.apply {
            initViewBinding()
            initView()
            initListener()
            initObserver()
        }
        lazyLoadDataIfPrepared()
    }

    override fun initToolbar(): View? {
        return CommonToolbar(mContext).apply {
            layoutParams = JmConstants.Toolbar.LAYOUT_PARAMS
            centerType = JmConstants.Toolbar.CENTER_TYPE
            centerTextColor = JmConstants.Toolbar.CENTER_TEXT_COLOR
            centerTextSize = JmConstants.Toolbar.CENTER_TEXT_SIZE.dp.toFloat()
            centerTextBold = JmConstants.Toolbar.CENTER_TEXT_IS_FAKE_BOLD
            showBottomLine = JmConstants.Toolbar.SHOW_BOTTOM_LINE
            toolbarHeight = JmConstants.Toolbar.TOOLBAR_HEIGHT
            toolbarColor = JmConstants.Toolbar.TOOLBAR_COLOR
            statusBarColor = JmConstants.Toolbar.STATUS_BAR_COLOR
            // 不填充状态栏
            fillStatusBar = false
        }
    }

    override fun initUiState() {
        mLoadService = LoadSir.getDefault().register(viewBinding!!.root, this)
    }

    private fun lazyLoadDataIfPrepared() {
        if (lifecycle.currentState >= Lifecycle.State.CREATED && isViewPrepared && !hasLoadedData) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            mLifecycleHandler.postDelayed({
                view?.let {
                    initData(arguments)
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

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun showLoading(loadingMsg: String?) {
        mLoadService.showLoading(loadingMsg = loadingMsg, showLoadingMsg = true)
    }

    override fun showContent() {
        mLoadService.showSuccess()
    }

    override fun showEmpty(emptyMsg: String?) {
        mLoadService.showEmpty(emptyMsg = emptyMsg)
    }

    override fun showNoNetwork(noNetworkMsg: String?) {
        mLoadService.showNoNetwork(noNetworkMsg = noNetworkMsg)
    }

    override fun showError(errorMsg: String?) {
        mLoadService.showError(errorMsg = errorMsg)
    }

    override fun toastErrorMsg(errorMsg: CharSequence?, t: Throwable?) {
        toast(errorMsg)
        t.toString().loge()
    }

    override fun onReload(v: View?) {
        showLoading(null)
        initData(null)
    }
}