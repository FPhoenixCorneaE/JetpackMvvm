package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.FragmentTestFirstBinding

class TestFirstFragment : BaseFragment<FragmentTestFirstBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun FragmentTestFirstBinding.initViewBinding() {
    }

    override fun contentViewMarginTop(): Int {
        return 0
    }

    override fun FragmentTestFirstBinding.initView() {
        mToolbar?.alpha = 0.8f
        showLoading("")
        tvText.text = "Test First Fragment"
    }

    override fun initData(savedInstanceState: Bundle?) {
        // 测试普通对象
        mHandler.postDelayed({
            showContent()
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        testFragmentViewModel.setOnBackPressed1Click()
    }

    companion object {
        fun newInstance(): TestFirstFragment {
            val fragment = TestFirstFragment()
            return fragment
        }
    }
}
