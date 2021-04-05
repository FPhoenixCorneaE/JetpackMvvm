package com.fphoenixcorneae.core.demo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.fphoenixcorneae.core.base.activity.AbstractBaseVmDbActivity
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.core.demo.databinding.ActivityTestFragmentBinding

class TestFragmentActivity : AbstractBaseVmDbActivity<BaseViewModel, ActivityTestFragmentBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test_fragment
    }

    override fun initToolbar() {
        mToolbar?.centerTextView?.text = "FragmentActivity"
    }

    override fun initListener() {
        mDataBinding.apply {
            rbFirst.setOnClickListener {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, TestFirstFragment.newInstance())
                        .commit()
            }
            rbSecond.setOnClickListener {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, TestSecondFragment.newInstance())
                        .commit()
            }
            rbThird.setOnClickListener {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, TestThirdFragment.newInstance())
                        .commit()
            }
            rbFour.setOnClickListener {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, TestFourFragment.newInstance())
                        .commit()
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame, TestFirstFragment.newInstance())
                .commit()
    }
}