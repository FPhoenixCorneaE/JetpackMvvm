package com.fphoenixcorneae.core.demo

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.activity.AbstractBaseActivity
import com.fphoenixcorneae.core.base.viewmodel.BaseViewModel
import com.fphoenixcorneae.core.demo.databinding.ActivityTestFragmentBinding

class TestFragmentActivity : AbstractBaseActivity<ActivityTestFragmentBinding>(R.layout.activity_test_fragment) {

    override fun initViewBinding(): ViewBinding {
        return ActivityTestFragmentBinding.inflate(layoutInflater)
    }

    override fun initToolbar() {
        mToolbar?.centerTextView?.text = "FragmentActivity"
    }

    override fun initListener() {
        mViewBinding.apply {
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