package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(
    value = [
        "adapter",
        "layoutManager"
    ],
    requireAll = false
)
fun RecyclerView.init(
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context),
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
}