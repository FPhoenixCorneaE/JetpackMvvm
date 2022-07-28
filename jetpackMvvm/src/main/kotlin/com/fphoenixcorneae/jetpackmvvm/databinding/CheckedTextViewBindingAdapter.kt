package com.fphoenixcorneae.jetpackmvvm.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.fphoenixcorneae.jetpackmvvm.widget.CheckedTextView
import com.fphoenixcorneae.jetpackmvvm.widget.OnCheckedChangeListener

@BindingAdapter("android:checked")
fun setChecked(view: android.widget.CheckedTextView, checked: Boolean) {
    if (view.isChecked != checked) {
        view.isChecked = checked
    }
}

@InverseBindingAdapter(attribute = "android:checked")
fun getChecked(view: android.widget.CheckedTextView): Boolean {
    return view.isChecked
}

@BindingAdapter(value = ["android:onCheckedChanged", "android:checkedAttrChanged"], requireAll = false)
fun setListener(
    view: CheckedTextView,
    onCheckedChangeListener: OnCheckedChangeListener?,
    checkedAttrChanged: InverseBindingListener?
) {
    if (checkedAttrChanged == null) {
        view.setOnCheckedChangeListener(onCheckedChangeListener)
    } else {
        view.setOnCheckedChangeListener { checkedTextView, isChecked ->
            onCheckedChangeListener?.invoke(checkedTextView, isChecked)
            checkedAttrChanged.onChange()
        }
    }
}