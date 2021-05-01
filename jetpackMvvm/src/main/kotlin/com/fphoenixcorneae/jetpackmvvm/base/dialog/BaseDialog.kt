package com.fphoenixcorneae.jetpackmvvm.base.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.jetpackmvvm.base.view.IView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.multistatus.MultiStatusLayoutManager
import com.fphoenixcorneae.jetpackmvvm.network.NetWorkState
import com.fphoenixcorneae.jetpackmvvm.network.NetworkStateManager
import com.fphoenixcorneae.util.ContextUtil

/**
 * @desc: Dialog 基类，自动把 ViewBinding 注入 Dialog
 * @since：2021-04-09 14:12
 */
abstract class BaseDialog<VB : ViewBinding> : DialogFragment(), IView<VB> {

    /** 视图是否加载完毕 */
    private var isViewPrepared = false

    /** 数据是否加载过了 */
    private var hasLoadedData = false

    /** 绑定视图 */
    private var viewBinding: VB? = null
    protected val mViewBinding get() = viewBinding!!

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

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
        viewBinding = initViewBinding()
        return mViewBinding.root
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
        createMultiStatusLayoutManager()
        initView()
        initListener()
        initViewObservable()
        lazyLoadDataIfPrepared()
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            // 在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                // 目前只有这两个dialog会出现边框
                if (this is ProgressDialog || this is DatePickerDialog) {
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
            attributes?.dimAmount = 0.0f
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netWorkState: NetWorkState) {}

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }

    fun show(activity: FragmentActivity) {
        super.show(activity.supportFragmentManager, null)
    }

    fun show(fragment: Fragment) {
        super.show(fragment.childFragmentManager, null)
    }
}