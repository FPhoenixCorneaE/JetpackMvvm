package com.fphoenixcorneae.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.fphoenixcorneae.core.CoreConstants
import com.fphoenixcorneae.core.base.view.IBaseView
import com.fphoenixcorneae.core.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.dsl.layout.LinearLayout
import com.fphoenixcorneae.ext.loggerE
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.ext.view.setTintColor
import com.fphoenixcorneae.titlebar.CommonTitleBar

/**
 * @desc：Fragment 基类
 * @date：2021/1/15 21:07
 */
abstract class AbstractBaseFragment : Fragment(), IBaseView {

    /** 视图是否加载完毕 */
    protected var isViewPrepared = false

    /** 数据是否加载过了 */
    protected var hasLoadedData = false

    /** Fragment 根视图 */
    protected var mRootView: View? = null

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
            layoutInflater.inflate(getLayoutId(), this, false)?.let { content ->
                // 添加内容
                addView(content)
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
        if (lifecycle.currentState == Lifecycle.State.STARTED && userVisibleHint && isViewPrepared && !hasLoadedData) {
            hasLoadedData = true
            initData(null)
        }
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