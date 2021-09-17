package com.fphoenixcorneae.jetpackmvvm.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.fphoenixcorneae.ext.logd
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.ActivityMainBinding
import com.fphoenixcorneae.jetpackmvvm.ext.defaultMMKV
import com.fphoenixcorneae.jetpackmvvm.ext.networkViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mHandler = Handler(Looper.getMainLooper())
    private val mViewModel by viewModels<MainViewModel>()

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mViewBinding.apply {
            viewModel = mViewModel
            imgUrl = "https://img-pre.ivsky.com/img/tupian/pre/202103/04/sunyunzhu_baise_lianyiqun.jpg"
            filterColor = Color.parseColor("#50ff0000")
        }

        defaultMMKV.encode("mmkv-version", "1.2.8")
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

    override fun onErrorClick() {
        Toast.makeText(mContext, "错误页面点击", Toast.LENGTH_SHORT).show()
    }
}