package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter(value = ["adapter", "isUserInputEnabled", "offscreenPageLimit"], requireAll = false)
fun initViewPager2(
    viewPager2: ViewPager2,
    adapter: RecyclerView.Adapter<*>? = null,
    isUserInputEnabled: Boolean? = true,
    @IntRange(from = 1) offscreenPageLimit: Int? = 1,
) {
    adapter?.let {
        viewPager2.adapter = it
    }
    isUserInputEnabled?.let {
        viewPager2.isUserInputEnabled = it
    }
    offscreenPageLimit?.let {
        viewPager2.offscreenPageLimit = it
    }
}