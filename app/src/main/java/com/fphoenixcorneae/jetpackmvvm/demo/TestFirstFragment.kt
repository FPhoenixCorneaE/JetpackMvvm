package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.FragmentTestFirstBinding
import com.fphoenixcorneae.toolbar.CommonToolbar

class TestFirstFragment : BaseFragment<FragmentTestFirstBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initViewBinding(): FragmentTestFirstBinding {
        return FragmentTestFirstBinding.inflate(layoutInflater)
    }

    override fun initView() {
        showLoading("")
        mViewBinding.tvText.text = "Test First Fragment"
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

    companion object {
        fun newInstance(): TestFirstFragment {
            val fragment = TestFirstFragment()
            return fragment
        }
    }
}
