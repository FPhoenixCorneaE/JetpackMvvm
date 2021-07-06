package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.loadAny
import coil.size.ViewSizeResolver
import coil.transform.*
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.fphoenixcorneae.ext.appContext
import com.fphoenixcorneae.ext.isNotNull
import com.fphoenixcorneae.ext.view.setTintColor
import com.fphoenixcorneae.jetpackmvvm.R

/**
 * @desc：ImageViewBindingAdapter
 * @date：2021/7/4 21:06
 */

val imageViewAttrs = run {
        val typedArray = appContext.obtainStyledAttributes(null, R.styleable.ImageView)
        typedArray.recycle()
    }

@BindingAdapter(
    value = [
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
    ], requireAll = false
)
fun ImageView.setSrc(
    imgUrl: Any?,
    @DrawableRes placeholderResId: Int = 0,
    placeholderDrawable: Drawable? = null,
    centerCrop: Boolean = true,
    isCircle: Boolean = false,
    isBlur: Boolean = false,
    isGrayscale: Boolean = false,
    @Px topLeftRadius: Float = 0f,
    @Px topRightRadius: Float = 0f,
    @Px bottomLeftRadius: Float = 0f,
    @Px bottomRightRadius: Float = 0f,
    @ColorInt filterColor: Int = 0,
) {
    loadAny(data = imgUrl) {
        crossfade(200)
        // 可选的，但是设置 ViewSizeResolver 可以通过限制预加载的大小来节省内存
        size(ViewSizeResolver(this@setSrc))
        if (placeholderDrawable.isNotNull()) {
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
            transformations.add(
                RoundedCornersTransformation(
                    topLeft = topLeftRadius,
                    topRight = topRightRadius,
                    bottomLeft = bottomLeftRadius,
                    bottomRight = bottomRightRadius
                )
            )
        }
        when {
            isBlur -> {
                transformations.add(BlurTransformation(this@setSrc.context, 20f))
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

@BindingAdapter(value = ["tint"], requireAll = false)
fun ImageView.setTint(tintColor: Int) {
    setTintColor(tintColor)
}