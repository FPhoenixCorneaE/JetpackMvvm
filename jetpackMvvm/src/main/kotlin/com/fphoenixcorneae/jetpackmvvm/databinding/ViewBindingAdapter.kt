package com.fphoenixcorneae.jetpackmvvm.databinding

import android.os.SystemClock
import android.view.View
import androidx.annotation.RestrictTo
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/**
 * @desc：ViewBindingAdapter
 * @date：2021/7/4 21:00
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class ViewBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("selected")
        fun View.setSelected(selected: Boolean) {
            isSelected = selected
        }

        @JvmStatic
        @BindingAdapter("visible")
        fun View.setVisible(visible: Boolean) {
            isVisible = visible
        }

        @JvmStatic
        @BindingAdapter(value = ["onSingleClick"], requireAll = false)
        fun View.setOnSingleClick(onClickListener: View.OnClickListener) {
            val hits = LongArray(2)
            setOnClickListener {
                System.arraycopy(hits, 1, hits, 0, hits.size - 1)
                hits[hits.size - 1] = SystemClock.uptimeMillis()
                if (hits[0] < SystemClock.uptimeMillis() - 500) {
                    onClickListener.onClick(it)
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["onSingleClick"], requireAll = false)
        fun View.setOnSingleClick(block: () -> Unit) {
            val hits = LongArray(2)
            setOnClickListener {
                System.arraycopy(hits, 1, hits, 0, hits.size - 1)
                hits[hits.size - 1] = SystemClock.uptimeMillis()
                if (hits[0] < SystemClock.uptimeMillis() - 500) {
                    block()
                }
            }
        }
    }
}