package com.fphoenixcorneae.core.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.fphoenixcorneae.core.base.activity.AbstractBaseActivity
import com.fphoenixcorneae.core.demo.databinding.ActivityTestListBinding

class TestListActivity : AbstractBaseActivity<ActivityTestListBinding>(R.layout.activity_test_list) {

    var list: ArrayList<String>? = null

    override fun initViewBinding(): ActivityTestListBinding {
        return ActivityTestListBinding.inflate(layoutInflater)
    }

    override fun initData(savedInstanceState: Bundle?) {
        showLoading("")
        Handler(Looper.getMainLooper()).postDelayed({
            showContent()
            setData()
        }, 2000)
    }

    private fun setData() {
        list = ArrayList()
        list?.add("test1")
        list?.add("test2")
        list?.add("test3")
        list?.add("test4")
        mViewBinding.rvRecycler.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
            ): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(
                        LayoutInflater.from(this@TestListActivity)
                                .inflate(android.R.layout.test_list_item, null)
                ) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                holder.itemView.findViewById<TextView>(android.R.id.text1).text = list!![position]
            }

            override fun getItemCount(): Int {
                return list!!.size
            }
        }
    }
}