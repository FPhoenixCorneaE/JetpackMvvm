package com.fphoenixcorneae.jetpackmvvm.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.fphoenixcorneae.common.ext.logd
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.ActivityMainBinding
import com.fphoenixcorneae.jetpackmvvm.ext.networkViewModel
import com.fphoenixcorneae.jetpackmvvm.startup.defaultMMKV

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())
    private val mViewModel by viewModels<MainViewModel>()

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mViewBinding.apply {
            viewModel = mViewModel
            imgData =
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp5.itc.cn%2Fq_70%2Fimages03%2F20200916%2F9670e51911c342f69c7de6e29e10a03b.gif&refer=http%3A%2F%2Fp5.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653718813&t=47572ac331539c55b7ee17424accbaaf"
//                "https://img0.baidu.com/it/u=3914584310,1839938303&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=300"
//            filterColor = Color.parseColor("#50ff0000")
        }

        defaultMMKV.encode("mmkv-version", "1.2.8")

        SplashDialog().show(this)
    }

    override fun initObserver() {
        mViewModel.twoWayBindingText.observe(this) {
            "twoWayBindingText: $it".logd()
        }
        networkViewModel.networkState.observe(this) {
            "networkState: $it".logd()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        mHandler.postDelayed({
            showContent()
            mViewBinding.tvTwoWayBinding.text = "two-way binding value changed"
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_multi_status_layout_demo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.loading_status_id -> showLoading(null)
            R.id.no_network_id -> showNoNetwork(null)
            R.id.error_view_id -> showError(null)
            R.id.empty_view_id -> showEmpty(null)
            R.id.content_view_id -> showContent()
            R.id.fragment_test_id -> {
                startActivity(Intent(this, TestFragmentActivity::class.java))
            }
            R.id.list_test_id -> {
                startActivity(Intent(this, TestListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}