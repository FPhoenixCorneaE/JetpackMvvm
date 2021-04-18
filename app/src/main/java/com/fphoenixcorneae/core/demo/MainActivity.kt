package com.fphoenixcorneae.core.demo

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.activity.AbstractBaseActivity
import com.fphoenixcorneae.core.demo.databinding.ActivityMainBinding

class MainActivity : AbstractBaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun initViewBinding(): ViewBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mViewBinding.apply {
            imgUrl = "https://img-pre.ivsky.com/img/tupian/pre/202103/04/sunyunzhu_baise_lianyiqun.jpg"
            filterColor = Color.parseColor("#50ff0000")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        showLoading("")
        mHandler.postDelayed({
            showContent()
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_multi_status_layout_demo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.loading_status_id -> showLoading("")
            R.id.no_network_id -> showNoNetwork()
            R.id.error_view_id -> showError()
            R.id.empty_view_id -> showEmpty()
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