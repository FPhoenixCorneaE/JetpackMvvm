package com.fphoenixcorneae.core.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.fragment.AbstractBaseFragment
import com.fphoenixcorneae.core.demo.databinding.FragmentTestFirstBinding

class TestFirstFragment : AbstractBaseFragment<FragmentTestFirstBinding>(R.layout.fragment_test_first) {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initViewBinding(): FragmentTestFirstBinding {
        return FragmentTestFirstBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mViewBinding.tvText.text = "Test First Fragment"
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        //测试普通对象
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
