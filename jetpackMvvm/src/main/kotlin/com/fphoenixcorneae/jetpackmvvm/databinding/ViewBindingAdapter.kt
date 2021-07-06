package com.fphoenixcorneae.jetpackmvvm.databinding

import android.os.SystemClock
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter(
    value = [
        "selected",
        "visible",
        "onSingleClick",
    ],
    requireAll = false
)
fun View.init(
    selected: Boolean? = false,
    visible: Boolean? = true,
    onSingleClick: View.OnClickListener? = null,
) {
    selected?.let {
        isSelected = it
    }
    visible?.let {
        isVisible = it
    }
    onSingleClick?.let {
        val hits = LongArray(2)
        setOnClickListener {
            System.arraycopy(hits, 1, hits, 0, hits.size - 1)
            hits[hits.size - 1] = SystemClock.uptimeMillis()
            if (hits[0] < SystemClock.uptimeMillis() - 500) {
                onSingleClick.onClick(it)
            }
        }
    }
}