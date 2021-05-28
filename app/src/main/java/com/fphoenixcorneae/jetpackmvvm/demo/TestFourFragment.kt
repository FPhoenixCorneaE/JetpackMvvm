package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.FragmentTestFourBinding

class TestFourFragment : BaseFragment<FragmentTestFourBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showEmpty(null)
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    companion object {
        fun newInstance(): TestFourFragment {
            val fragment = TestFourFragment()
            return fragment
        }
    }

    override fun initViewBinding(): FragmentTestFourBinding {
        return FragmentTestFourBinding.inflate(layoutInflater)
    }
}
