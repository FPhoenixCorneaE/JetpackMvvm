package com.fphoenixcorneae.jetpackmvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckedTextView
import androidx.appcompat.widget.AppCompatCheckedTextView

/**
 * @desc：CheckedTextView
 * @date：2022/04/18 09:49
 */
class CheckedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatCheckedTextView(context, attrs, defStyleAttr) {

    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        mOnCheckedChangeListener?.invoke(this, checked)
    }

    fun setOnCheckedChangeListener(onCheckedChangeListener: OnCheckedChangeListener?) {
        mOnCheckedChangeListener = onCheckedChangeListener
    }
}

/**
 * @desc：OnCheckedChangeListener
 * @date：2022/04/18 09:50
 */
typealias OnCheckedChangeListener = (
    @ParameterName("checkedTextView") CheckedTextView,
    @ParameterName("isChecked") Boolean
) -> Unit

