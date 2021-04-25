package com.fphoenixcorneae.core.base.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.core.network.NetWorkState
import com.fphoenixcorneae.core.network.NetworkStateManager

/**
 * @desc: Dialog 基类，自动把 ViewBinding 注入 Dialog
 * @since：2021-04-09 14:12
 */
abstract class AbstractBaseDialog<VB : ViewBinding> : DialogFragment() {

    private val handler = Handler(Looper.getMainLooper())

    // 是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mActivity: AppCompatActivity

    /** 绑定视图 */
    private var viewBinding: VB? = null
    protected val mViewBinding get() = viewBinding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = initViewBinding()
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        initView(savedInstanceState)
        createObserver()
        initData()
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.apply {
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
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    fun show(activity: FragmentActivity) {
        super.show(activity.supportFragmentManager, null)
    }

    fun show(fragment: Fragment) {
        super.show(fragment.childFragmentManager, null)
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netWorkState:  NetWorkState) {}

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            handler.postDelayed({
                lazyLoadData()
                //在Fragment中，只有懒加载过了才能开启网络变化监听
                NetworkStateManager.networkState.observeInFragment(
                        this
                ) {
                    //不是首次订阅时调用方法，防止数据第一次监听错误
                    if (!isFirst) {
                        onNetworkStateChanged(it)
                    }
                }
                isFirst = false
            }, lazyLoadTime())
        }
    }

    /**
     * 将非该Fragment绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            // 显示弹窗
            viewModel.loadingChange.showDialog.observeInFragment(this) {
                showLoading(it)
            }
            // 关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInFragment(this) {
                dismissLoading()
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
     * Fragment执行 onViewCreated 后触发的方法
     */
    open fun initData() {}

    open fun showLoading(message: String = "请求网络中...") {}

    open fun dismissLoading() {}

    /**
     * 初始化 ViewBinding 操作
     */
    abstract fun initViewBinding(): VB

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()
}