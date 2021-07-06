package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.annotation.IntRange
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.fphoenixcorneae.ext.appContext
import com.fphoenixcorneae.jetpackmvvm.R

/**
 * @desc：ViewPager2BindingAdapter
 * @date：2021/7/4 21:08
 */

val viewPager2Attrs = run {
    val typedArray = appContext.obtainStyledAttributes(null, R.styleable.ViewPager2)
    typedArray.recycle()
}

@BindingAdapter(
    value = [
        "adapter",
        "userInputEnabled",
        "offscreenPageLimit"
    ], requireAll = false
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