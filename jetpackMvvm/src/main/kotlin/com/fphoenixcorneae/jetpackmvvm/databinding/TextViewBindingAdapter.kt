package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(
    value = [
        "strikeThru",
    ],
    requireAll = false
)
fun TextView.init(enable: Boolean) {
    // 添加删除线
    paintFlags = if (enable) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}