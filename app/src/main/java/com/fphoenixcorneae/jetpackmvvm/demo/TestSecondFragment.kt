package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.FragmentTestSecondBinding

class TestSecondFragment : BaseFragment<FragmentTestSecondBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initViewBinding(): FragmentTestSecondBinding {
        return FragmentTestSecondBinding.inflate(layoutInflater)
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showNoNetwork(null)
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    companion object {
        fun newInstance(): TestSecondFragment {
            val fragment = TestSecondFragment()
            return fragment
        }
    }
}
