package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.FragmentTestThirdBinding

class TestThirdFragment : BaseFragment<FragmentTestThirdBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showError(null)
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        testFragmentViewModel.setOnBackPressed3Click()
    }

    companion object {
        fun newInstance(): TestThirdFragment {
            val fragment = TestThirdFragment()
            return fragment
        }
    }

    override fun FragmentTestThirdBinding.initViewBinding() {
    }
}
