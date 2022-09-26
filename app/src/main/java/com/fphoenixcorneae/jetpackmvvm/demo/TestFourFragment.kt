package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.fphoenixcorneae.jetpackmvvm.base.fragment.BaseFragment
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.FragmentTestFourBinding

class TestFourFragment : BaseFragment<FragmentTestFourBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())
    private val mTestDialog by lazy { TestDialog() }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
        mHandler.postDelayed({
            showEmpty(null)
        }, 2000)

        mTestDialog.show(this)
        mHandler.postDelayed(2000) {
            mTestDialog.show(this)
        }
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        testFragmentViewModel.setOnBackPressed4Click()
    }

    companion object {
        fun newInstance(): TestFourFragment {
            val fragment = TestFourFragment()
            return fragment
        }
    }

    override fun FragmentTestFourBinding.initViewBinding() {
    }
}
