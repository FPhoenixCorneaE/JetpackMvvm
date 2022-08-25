package com.fphoenixcorneae.jetpackmvvm.databinding

import android.os.SystemClock
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/**
 * 设置可见或隐藏
 * @param visible true->可见，false->隐藏
 */
@BindingAdapter("isVisible")
fun isVisible(view: View, visible: Boolean) {
    view.isVisible = visible
}

/**
 * 设置不可见或可见
 * @param invisible true->不可见，false->可见
 */
@BindingAdapter("isInvisible")
fun isInvisible(view: View, invisible: Boolean) {
    view.isInvisible = invisible
}

/**
 * 设置是否选中
 * @param selected true->选中，false->未选中
 */
@BindingAdapter("isSelected")
fun isSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

/**
 * 设置单次点击监听
 */
@BindingAdapter(value = ["onSingleClick"], requireAll = false)
fun setOnSingleClick(
    view: View,
    onSingleClick: View.OnClickListener,
) {
    val hits = LongArray(2)
    view.setOnClickListener {
        System.arraycopy(hits, 1, hits, 0, hits.size - 1)
        hits[hits.size - 1] = SystemClock.uptimeMillis()
        if (hits[0] < SystemClock.uptimeMillis() - 500) {
            onSingleClick.onClick(it)
        }
    }
}

/**
 * 设置多次点击监听
 * @param onClickListener 点击监听
 * @param times      点击次数
 * @param duration        有效时间
 */
@BindingAdapter(
    value = [
        "onMultiClickTimes",
        "onMultiClickValidDuration",
        "onMultiClick",
    ],
    requireAll = false
)
fun setOnMultiClick(
    view: View,
    times: Int,
    duration: Long,
    onClickListener: View.OnClickListener,
) {
    var clickTimes = times
    if (clickTimes <= 2) {
        clickTimes = 2
    }
    var validDuration = duration
    if (validDuration <= 1_000) {
        validDuration = 1_000
    }
    var hits = LongArray(clickTimes + 1)
    view.setOnClickListener {
        // 将hits数组内的所有元素左移一个位置
        System.arraycopy(hits, 1, hits, 0, hits.size - 1)
        // 获得当前系统已经启动的时间
        hits[hits.lastIndex] = SystemClock.uptimeMillis()
        if (hits.last() - hits[1] <= validDuration && hits.last() - hits.first() >= validDuration) {
            // 相关逻辑操作
            onClickListener.onClick(it)
            // 初始化数组点击时间为上一次点击时间
            hits = hits.flatMap { listOf(hits.last()) }.toLongArray()
        }
    }
}

/**
 * 设置长按监听
 */
@BindingAdapter(value = ["onLongClick"], requireAll = false)
fun setOnLongClick(
    view: View,
    onLongClickListener: View.OnLongClickListener,
) {
    view.setOnLongClickListener {
        return@setOnLongClickListener onLongClickListener.onLongClick(it)
    }
}