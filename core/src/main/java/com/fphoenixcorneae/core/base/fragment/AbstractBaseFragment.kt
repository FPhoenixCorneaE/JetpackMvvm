package com.fphoenixcorneae.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.fphoenixcorneae.core.base.view.IBaseView
import com.fphoenixcorneae.core.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.ext.loggerE
import com.fphoenixcorneae.ext.toast

/**
 * @desc：Fragment 基类
 * @date：2021/1/15 21:07
 */
abstract class AbstractBaseFragment : Fragment(), IBaseView {

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    /** 视图是否加载完毕 */
    protected var isViewPrepared = false

    /** 数据是否加载过了 */
    protected var hasLoadedData = false

    /** Fragment 根视图 */
    private var mRootView: View? = null

    /** 多状态布局管理器 */
    private var mMultiStatusLayoutManager: MultiStatusLayoutManager? = null

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
        mRootView = inflater.inflate(
            getLayoutId(), container, false
        )
        return mRootView
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
        initParam()
        initView()
        initListener()
        initViewObservable()
        lazyLoadDataIfPrepared()
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepared && !hasLoadedData) {
            hasLoadedData = true
            initData(null)
        }
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