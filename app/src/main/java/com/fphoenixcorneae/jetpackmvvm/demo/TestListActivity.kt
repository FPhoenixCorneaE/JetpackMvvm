package com.fphoenixcorneae.jetpackmvvm.demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fphoenixcorneae.jetpackmvvm.base.activity.BaseActivity
import com.fphoenixcorneae.jetpackmvvm.demo.databinding.ActivityTestListBinding

class TestListActivity : BaseActivity<ActivityTestListBinding>() {

    var list: ArrayList<String>? = null

    override fun ActivityTestListBinding.initViewBinding() {
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