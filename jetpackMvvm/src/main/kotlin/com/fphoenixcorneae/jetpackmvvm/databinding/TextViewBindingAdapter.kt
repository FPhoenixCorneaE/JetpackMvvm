package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * 添加删除线
 */
@BindingAdapter(value = ["strikeThru"], requireAll = false)
fun setStrikeThru(textView: TextView, enable: Boolean) {
    textView.paintFlags = if (enable) {
        textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

/**
 * 添加下划线
 */
@BindingAdapter(value = ["underline"], requireAll = false)
fun setUnderline(textView: TextView, enable: Boolean) {
    textView.paintFlags = if (enable) {
        textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    } else {
        textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
    }
}

/**
 * 设置粗体
 */
@BindingAdapter("isFakeBoldText")
fun setFakeBoldText(textView: TextView, isFakeBoldText: Boolean) {
    textView.paint.isFakeBoldText = isFakeBoldText
}