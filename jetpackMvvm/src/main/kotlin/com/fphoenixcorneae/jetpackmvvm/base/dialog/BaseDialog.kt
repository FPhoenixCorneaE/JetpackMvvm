package com.fphoenixcorneae.jetpackmvvm.base.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.fphoenixcorneae.common.ext.loge
import com.fphoenixcorneae.common.ext.toast
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.jetpackmvvm.base.view.BaseView
import com.fphoenixcorneae.jetpackmvvm.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.jetpackmvvm.ext.getViewBinding
import com.fphoenixcorneae.jetpackmvvm.lifecycle.DialogLifecycleImpl
import com.fphoenixcorneae.jetpackmvvm.lifecycle.LifecycleHandler
import com.fphoenixcorneae.jetpackmvvm.livedata.EventObserver
import com.fphoenixcorneae.jetpackmvvm.uistate.showEmpty
import com.fphoenixcorneae.jetpackmvvm.uistate.showError
import com.fphoenixcorneae.jetpackmvvm.uistate.showLoading
import com.fphoenixcorneae.jetpackmvvm.uistate.showNoNetwork
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlin.properties.Delegates

/**
 * @desc: Dialog 基类，自动把 ViewBinding 注入 Dialog
 * @since：2021-04-09 14:12
 */
abstract class BaseDialog<VB : ViewDataBinding> : DialogFragment(), BaseView<VB>, Callback.OnReloadListener {

    init {
        lifecycle.addObserver(DialogLifecycleImpl())
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

    private var mOnDismissListener: ((DialogInterface) -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = getViewBinding<VB>(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return viewBinding?.root
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

    @SuppressLint("ObsoleteSdkInt")
    override fun onStart() {
        super.onStart()
        dialog?.apply {
            // 是否可点击外部消失
            setCanceledOnTouchOutside(canceledOnTouchOutside)
            // 是否可取消
            setCancelable(isCancelable)
            window?.apply {
                // 去掉 dialog 默认的 padding
                decorView.setPadding(0, 0, 0, 0)
                setBackgroundDrawable(getBackground())
                setGravity(getGravity())
                setLayout(getWidth(), getHeight())
                attributes = attributes?.apply {
                    windowAnimations = getWindowAnimations()
                    dimAmount = getDimAmount()
                }
            }
        }
    }

    /**
     * 若要修改背景可重写该方法
     */
    protected open fun getBackground(): Drawable {
        return ColorDrawable()
    }

    /**
     * 若要修改重力方向可重写该方法
     */
    protected open fun getGravity(): Int {
        return Gravity.CENTER
    }

    /**
     * 若要修改弹窗动画可重写该方法
     */
    protected open fun getWindowAnimations(): Int {
        return R.style.DialogAnimation
    }

    /**
     * 若要修改宽度可重写该方法
     */
    protected open fun getWidth(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     * 若要修改高度可重写该方法
     */
    protected open fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    /**
     * 若要修改模糊度可重写该方法
     */
    protected open fun getDimAmount(): Float {
        return 0.4f
    }

    /**
     * 若要修改[canceledOnTouchOutside]可重写该属性
     */
    protected open var canceledOnTouchOutside = true

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

    fun show(activity: FragmentActivity, tag: String? = null) {
        super.show(activity.supportFragmentManager, tag)
    }

    fun show(fragment: Fragment, tag: String? = null) {
        super.show(fragment.childFragmentManager, tag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnDismissListener?.invoke(dialog)
    }

    fun setOnDismissListener(onDismissListener: (DialogInterface) -> Unit) = apply {
        mOnDismissListener = onDismissListener
    }

    override fun onReload(v: View?) {
        showLoading(null)
        initData(null)
    }
}