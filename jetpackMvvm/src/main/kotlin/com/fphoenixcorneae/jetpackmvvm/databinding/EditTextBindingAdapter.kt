package com.fphoenixcorneae.jetpackmvvm.databinding

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter

/**
 * 获得/清除焦点
 */
@BindingAdapter(value = ["requestFocus"], requireAll = false)
fun requestFocus(editText: EditText, b: Boolean) {
    if (b) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.setSelection(editText.text.length)
        editText.requestFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    } else {
        editText.clearFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}