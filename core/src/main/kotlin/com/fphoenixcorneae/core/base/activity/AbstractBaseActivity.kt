package com.fphoenixcorneae.core.base.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import com.fphoenixcorneae.core.CoreConstants
import com.fphoenixcorneae.core.R
import com.fphoenixcorneae.core.base.view.IBaseView
import com.fphoenixcorneae.core.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.dsl.layout.LinearLayout
import com.fphoenixcorneae.ext.loggerE
import com.fphoenixcorneae.ext.toast
import com.fphoenixcorneae.ext.view.setTintColor
import com.fphoenixcorneae.titlebar.CommonTitleBar

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

    /** 标题栏 */
    protected var mToolbar: CommonTitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(getLayoutId())
        createMultiStatusLayoutManager()
        initToolbar()
        initParam()
        initView()
        initListener()
        initViewObservable()
        initData(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
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
            layoutInflater.inflate(layoutResID, this, false)?.let { content ->
                // 添加内容
                addView(content)
            }
        }
        super.setContentView(contentView)
    }

    override fun createToolbar(): View? {
        mToolbar = CommonTitleBar(this).apply {
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