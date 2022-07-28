package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fphoenixcorneae.jetpackmvvm.listener.OnLoadMoreListener
import com.fphoenixcorneae.jetpackmvvm.listener.OnScrollLoadMoreListener

@BindingAdapter(value = [
    "adapter", "manager", "hasFixedSize", "clipToPadding", "itemViewCacheSize", "itemAnimator",
], requireAll = false)
fun initRecycler(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>?,
    layoutManager: RecyclerView.LayoutManager?,
    hasFixedSize: Boolean?,
    clipToPadding: Boolean?,
    itemViewCacheSize: Int?,
    itemAnimator: RecyclerView.ItemAnimator?,
) {
    adapter?.let { recyclerView.adapter = it }
    layoutManager?.let { recyclerView.layoutManager = it }
    hasFixedSize?.let { recyclerView.setHasFixedSize(it) }
    clipToPadding?.let { recyclerView.clipToPadding = it }
    itemViewCacheSize?.let { recyclerView.setItemViewCacheSize(it) }
    itemAnimator?.let { recyclerView.itemAnimator = it }
}

@BindingAdapter(value = ["onLoadMore"], requireAll = false)
fun setOnLoadMoreListener(
    recyclerView: RecyclerView,
    onLoadMoreListener: OnLoadMoreListener,
) {
    recyclerView.addOnScrollListener(OnScrollLoadMoreListener(onLoadMoreListener = onLoadMoreListener))
}
