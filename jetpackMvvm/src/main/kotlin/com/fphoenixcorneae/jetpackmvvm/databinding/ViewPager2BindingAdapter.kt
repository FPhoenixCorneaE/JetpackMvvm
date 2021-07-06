package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter(
    value = [
        "adapter",
        "userInputEnabled",
        "offscreenPageLimit"
    ],
    requireAll = false
)
fun ViewPager2.init(
    adapter: RecyclerView.Adapter<*>,
    userInputEnabled: Boolean = true,
    @IntRange(from = 1) offscreenPageLimit: Int = 1,
) {
    this.adapter = adapter
    isUserInputEnabled = userInputEnabled
    setOffscreenPageLimit(offscreenPageLimit)
}