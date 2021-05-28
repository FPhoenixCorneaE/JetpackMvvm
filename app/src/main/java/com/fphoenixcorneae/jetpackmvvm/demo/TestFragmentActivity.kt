package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.ActivityTestFragmentBinding

class TestFragmentActivity : BaseActivity<ActivityTestFragmentBinding>() {

    override fun initViewBinding(): ActivityTestFragmentBinding {
        return ActivityTestFragmentBinding.inflate(layoutInflater)
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

    override fun initView() {
        mToolbar?.centerTextView?.text = "FragmentActivity"
    }

    override fun initData(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, TestFirstFragment.newInstance())
            .commit()
    }
}