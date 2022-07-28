package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fphoenixcorneae.jetpackmvvm.listener.OnLoadMoreListener
import com.fphoenixcorneae.jetpackmvvm.listener.OnScrollLoadMoreListener

@BindingAdapter(value = ["adapter", "manager"], requireAll = false)
fun initRecycler(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>? = null,
    layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(recyclerView.context),
) {
    adapter?.let {
        recyclerView.adapter = it
    }
    layoutManager?.let {
        recyclerView.layoutManager = it
    }
}

@BindingAdapter(value = ["onLoadMore"], requireAll = false)
fun setOnLoadMoreListener(
    recyclerView: RecyclerView,
    onLoadMoreListener: OnLoadMoreListener
) {
    recyclerView.addOnScrollListener(OnScrollLoadMoreListener(onLoadMoreListener = onLoadMoreListener))
}
