package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.annotation.RestrictTo
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc：RecyclerViewBindingAdapter
 * @date：2021/7/4 21:08
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class RecyclerViewBindingAdapter {

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
}