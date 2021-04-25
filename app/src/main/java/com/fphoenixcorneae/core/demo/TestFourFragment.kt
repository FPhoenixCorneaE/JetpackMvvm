package com.fphoenixcorneae.core.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.core.base.fragment.AbstractBaseFragment
import com.fphoenixcorneae.core.demo.databinding.FragmentTestFourBinding

class TestFourFragment : AbstractBaseFragment<FragmentTestFourBinding>(R.layout.fragment_test_four) {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showEmpty()
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
