package com.fphoenixcorneae.core.databinding

import android.graphics.drawable.Drawable
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.loadAny
import coil.size.ViewSizeResolver
import coil.transform.*
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.fphoenixcorneae.ext.isNonNull

/**
 * @desc：@BindingAdapter 自定义属性
 * @date：2021-04-11-22:33
 */
@BindingAdapter("selected")
fun setSelected(view: View, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter(value = [
    "imgUrl",
    "placeholderResId",
    "placeholderDrawable",
    "centerCrop",
    "isCircle",
    "isBlur",
    "isGrayscale",
    "topLeftRadius",
    "topRightRadius",
    "bottomLeftRadius",
    "bottomRightRadius",
    "filterColor",
], requireAll = false)
fun setSrc(
        target: ImageView,
        imgUrl: Any?,
        placeholderResId: Int = 0,
        placeholderDrawable: Drawable? = null,
        centerCrop: Boolean = true,
        isCircle: Boolean = false,
        isBlur: Boolean = false,
        isGrayscale: Boolean = false,
        @Px topLeftRadius: Float = 0f,
        @Px topRightRadius: Float = 0f,
        @Px bottomLeftRadius: Float = 0f,
        @Px bottomRightRadius: Float = 0f,
        @ColorInt filterColor: Int = 0
) {
    target.loadAny(data = imgUrl) {
        crossfade(200)
        // 可选的，但是设置 ViewSizeResolver 可以通过限制预加载的大小来节省内存
        size(ViewSizeResolver(target))
        if (placeholderDrawable.isNonNull()) {
            placeholder(placeholderDrawable)
        } else {
            placeholder(placeholderResId)
        }
        val transformations = arrayListOf<Transformation>()
        if (centerCrop) {
            transformations.add(CropTransformation(CropTransformation.CropType.CENTER))
        }
        if (isCircle) {
            transformations.add(CircleCropTransformation())
        } else {
            transformations.add(RoundedCornersTransformation(
                    topLeft = topLeftRadius,
                    topRight = topRightRadius,
                    bottomLeft = bottomLeftRadius,
                    bottomRight = bottomRightRadius
            ))
        }
        when {
            isBlur -> {
                transformations.add(BlurTransformation(target.context, 20f))
            }
            isGrayscale -> {
                transformations.add(GrayscaleTransformation())
            }
            filterColor != 0 -> {
                transformations.add(ColorFilterTransformation(filterColor))
            }
        }
        transformations(transformations)
    }
}

@BindingAdapter("android:text")
fun setText(view: TextView, text: CharSequence?) {
    val oldText = view.text
    if (text === oldText || text == null && oldText.isEmpty()) {
        return
    }
    if (text is Spanned) {
        if (text == oldText) {
            // No change in the spans, so don't set anything.
            return
        }
    } else if (TextUtils.equals(text, oldText)) {
        // No content changes, so don't set anything.
        return
    }
    view.text = text
}