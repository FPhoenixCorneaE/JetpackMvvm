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
import com.fphoenixcorneae.common.ext.isNotNull
import com.fphoenixcorneae.common.ext.view.setTintColor

@BindingAdapter(
    value = [
        "imgData",
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
    ],
    requireAll = false
)
fun ImageView.loadData(
    imgData: Any?,
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
    loadAny(data = imgData) {
        crossfade(200)
        // 可选的，但是设置 ViewSizeResolver 可以通过限制预加载的大小来节省内存
        size(ViewSizeResolver(this@loadData))
        // 占位图
        if (placeholderDrawable.isNotNull()) {
            placeholder(placeholderDrawable)
            error(placeholderDrawable)
        } else {
            placeholder(placeholderResId)
            error(placeholderResId)
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
                // 高斯模糊
                transformations.add(BlurTransformation(context, 20f))
            }
            isGrayscale -> {
                // 灰度
                transformations.add(GrayscaleTransformation())
            }
            filterColor != 0 -> {
                // 过滤颜色
                transformations.add(ColorFilterTransformation(filterColor))
            }
        }
        transformations(transformations)
    }
}

@BindingAdapter(value = ["tint"], requireAll = false)
fun setTintColor(imageView: ImageView, tintColor: Int) {
    imageView.setTintColor(tintColor)
}