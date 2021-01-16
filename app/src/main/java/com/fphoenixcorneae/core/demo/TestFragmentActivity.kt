package com.fphoenixcorneae.core.demo

import android.os.Bundle
import com.fphoenixcorneae.core.base.activity.AbstractBaseActivity
import kotlinx.android.synthetic.main.activity_test_fragment.*

class TestFragmentActivity : AbstractBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test_fragment
    }

    override fun initListener() {
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

    override fun initData(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, TestFirstFragment.newInstance())
            .commit()
    }
}