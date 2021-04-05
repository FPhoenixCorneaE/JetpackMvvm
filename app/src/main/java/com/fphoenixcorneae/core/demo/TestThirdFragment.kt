package com.fphoenixcorneae.core.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.core.base.fragment.AbstractBaseFragment

class TestThirdFragment : AbstractBaseFragment() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun getLayoutId(): Int {
        return R.layout.fragment_test_third
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showError()
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    companion object {
        fun newInstance(): TestThirdFragment {
            val fragment = TestThirdFragment()
            return fragment
        }
    }
}
