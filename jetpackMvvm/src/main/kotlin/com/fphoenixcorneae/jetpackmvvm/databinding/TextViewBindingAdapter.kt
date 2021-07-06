package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.fphoenixcorneae.ext.appContext
import com.fphoenixcorneae.jetpackmvvm.R

/**
 * @desc：TextViewBindingAdapter
 * @date：2021/7/4 21:06
 */
val textViewAttrs = run {
    val typedArray = appContext.obtainStyledAttributes(null, R.styleable.TextView)
    typedArray.recycle()
}

/**
 * 添加分割线
 */
@BindingAdapter(
    value = [
        "strikeThruText",
    ], requireAll = false
)
fun TextView.setStrikeThruText(enable: Boolean) {
    // 添加删除线
    paintFlags = if (enable) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}