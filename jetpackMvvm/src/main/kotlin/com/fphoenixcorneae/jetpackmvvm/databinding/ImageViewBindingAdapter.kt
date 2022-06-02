package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.*
import com.commit451.coiltransformations.BlurTransformation
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.commit451.coiltransformations.GrayscaleTransformation
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
    @DrawableRes placeholderResId: Int? = null,
    placeholderDrawable: Drawable? = null,
    centerCrop: Boolean? = null,
    isCircle: Boolean? = null,
    isBlur: Boolean? = null,
    isGrayscale: Boolean? = null,
    @FloatRange(from = 0.0) @Px topLeftRadius: Float? = null,
    @FloatRange(from = 0.0) @Px topRightRadius: Float? = null,
    @FloatRange(from = 0.0) @Px bottomLeftRadius: Float? = null,
    @FloatRange(from = 0.0) @Px bottomRightRadius: Float? = null,
    @ColorInt filterColor: Int? = null,
) {
    load(data = imgData) {
        crossfade(200)
        // 可选的，但是设置 ViewSizeResolver 可以通过限制预加载的大小来节省内存
        size(ViewSizeResolver(this@loadData))
        // 占位图
        placeholderResId?.let {
            placeholder(it)
            error(it)
        } ?: placeholderDrawable?.let {
            placeholder(it)
            error(it)
        }

        val transformations = arrayListOf<Transformation>()
        if (centerCrop == true) {
            transformations.add(CropTransformation(CropTransformation.CropType.CENTER))
        }
        if (isCircle == true) {
            transformations.add(CircleCropTransformation())
        } else if (topLeftRadius != null || topRightRadius != null || bottomLeftRadius != null || bottomRightRadius != null) {
            transformations.add(
                RoundedCornersTransformation(
                    topLeft = topLeftRadius ?: 0f,
                    topRight = topRightRadius ?: 0f,
                    bottomLeft = bottomLeftRadius ?: 0f,
                    bottomRight = bottomRightRadius ?: 0f
                )
            )
        }
        if (isBlur == true) {
            // 高斯模糊
            transformations.add(BlurTransformation(context, 20f))
        }
        if (isGrayscale == true) {
            // 灰度
            transformations.add(GrayscaleTransformation())
        }
        if (filterColor != null) {
            // 过滤颜色
            transformations.add(ColorFilterTransformation(filterColor))
        }
        transformations(transformations)
    }
}

@BindingAdapter(value = ["tint"], requireAll = false)
fun setTintColor(imageView: ImageView, tintColor: Int) {
    imageView.setTintColor(tintColor)
}