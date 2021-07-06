package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fphoenixcorneae.ext.appContext
import com.fphoenixcorneae.jetpackmvvm.R

/**
 * @desc：RecyclerViewBindingAdapter
 * @date：2021/7/4 21:08
 */

val recyclerViewAttrs = run {
    val typedArray = appContext.obtainStyledAttributes(null, R.styleable.RecyclerView)
    typedArray.recycle()
}

@BindingAdapter(
    value = [
        "adapter",
        "layoutManager"
    ], requireAll = false
)
fun RecyclerView.init(
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context),
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
}