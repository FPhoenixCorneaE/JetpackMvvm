package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.Paint
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.databinding.BindingAdapter

/**
 * @desc：TextViewBindingAdapter
 * @date：2021/7/4 21:06
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class TextViewBindingAdapter {

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
}