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
    adapter: RecyclerView.Adapter<*>? = null,
    userInputEnabled: Boolean? = true,
    @IntRange(from = 1) offscreenPageLimit: Int? = 1,
) {
    adapter?.also {
        this.adapter = it
    }
    userInputEnabled?.also {
        isUserInputEnabled = it
    }
    offscreenPageLimit?.also {
        setOffscreenPageLimit(it)
    }
}