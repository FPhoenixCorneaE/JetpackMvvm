package com.fphoenixcorneae.core.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.core.base.fragment.AbstractBaseFragment
import com.fphoenixcorneae.core.demo.databinding.FragmentTestSecondBinding

class TestSecondFragment : AbstractBaseFragment<FragmentTestSecondBinding>(R.layout.fragment_test_second) {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showNoNetwork()
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

    override fun initViewBinding(): FragmentTestSecondBinding {
        return FragmentTestSecondBinding.inflate(layoutInflater)
    }
}
