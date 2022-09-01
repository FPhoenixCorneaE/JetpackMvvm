package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.fphoenixcorneae.common.ext.logd
import com.fphoenixcorneae.common.ext.navigate
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.ActivityTestFragmentBinding
import com.fphoenixcorneae.toolbar.CommonToolbar
import kotlinx.coroutines.launch

class TestFragmentActivity : BaseActivity<ActivityTestFragmentBinding>() {

    override fun ActivityTestFragmentBinding.initViewBinding() {
    }

    override fun ActivityTestFragmentBinding.initView() {
        mToolbar?.alpha = 0.8f
        (mToolbar as CommonToolbar?)?.centerTextView?.text = "FragmentActivity"
    }

    override fun ActivityTestFragmentBinding.initListener() {
        mViewBinding.apply {
            rbFirst.setOnClickListener {
                findNavController(R.id.frame).navigate(R.id.fragmentTestFirst)
            }
            rbSecond.setOnClickListener {
                findNavController(R.id.frame).navigate(R.id.fragmentTestSecond)
            }
            rbThird.setOnClickListener {
                findNavController(R.id.frame).navigate(R.id.fragmentTestThird)
            }
            rbFour.setOnClickListener {
                findNavController(R.id.frame).navigate(R.id.fragmentTestFour)
            }
        }
    }

    override fun ActivityTestFragmentBinding.initObserver() {
        lifecycleScope.launch {
            testFragmentViewModel.onBackPressed4.collect {
                "testFragmentViewModel: onBackPressed4: $it".logd()
                it?.let {
                    rbThird.performClick()
                }
            }
        }
        lifecycleScope.launch {
            testFragmentViewModel.onBackPressed3.collect {
                "testFragmentViewModel: onBackPressed3: $it".logd()
                it?.let {
                    rbSecond.performClick()
                }
            }
        }
        lifecycleScope.launch {
            testFragmentViewModel.onBackPressed2.collect {
                "testFragmentViewModel: onBackPressed2: $it".logd()
                it?.let {
                    rbFirst.performClick()
                }
            }
        }
        lifecycleScope.launch {
            testFragmentViewModel.onBackPressed1.collect {
                "testFragmentViewModel: onBackPressed1: $it".logd()
                it?.let {
                    finish()
                }
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, TestFirstFragment.newInstance())
            .commit()
    }
}