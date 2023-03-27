package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.commit451.coiltransformations.BlurTransformation
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.commit451.coiltransformations.GrayscaleTransformation
import com.fphoenixcorneae.common.ext.Dp
import com.fphoenixcorneae.common.ext.view.setTintColor

/**
 * Coil 加载图片：更快、更轻量级、更容易使用、更流行
 * @param imgData             图片数据, 可以是url、resource、file、and more...
 * @param placeholderResId    占位图, resource id
 * @param placeholderDrawable 占位图, drawable
 * @param centerCrop          是否中心裁剪
 * @param isCircle            是否裁剪成圆形
 * @param roundedCornerRadius 圆角半径, dp value
 * @param topLeftRadius       左上半径, dp value
 * @param topRightRadius      右上半径, dp value
 * @param bottomLeftRadius    左下半径, dp value
 * @param bottomRightRadius   右下半径, dp value
 * @param isBlur              是否高斯模糊
 * @param blurRadius          高斯模糊半径
 * @param isGrayscale         是否灰度
 * @param filterColor         过滤颜色
 * @param lifecycleOwner      the [Lifecycle] for this request.
 */
@BindingAdapter(
    value = [
        "imgData", "placeholderResId", "placeholderDrawable", "errorResId", "errorDrawable", "centerCrop", "isCircle",
        "roundedCornerRadius", "topLeftRadius", "topRightRadius", "bottomLeftRadius", "bottomRightRadius", "isBlur",
        "blurRadius", "isGrayscale", "filterColor", "lifecycleOwner",
    ],
    requireAll = false
)
fun ImageView.loadData(
    imgData: Any?,
    @DrawableRes placeholderResId: Int? = null,
    placeholderDrawable: Drawable? = null,
    @DrawableRes errorResId: Int? = null,
    errorDrawable: Drawable? = null,
    centerCrop: Boolean? = null,
    isCircle: Boolean? = null,
    @FloatRange(from = 0.0) roundedCornerRadius: Float? = null,
    @FloatRange(from = 0.0) topLeftRadius: Float? = null,
    @FloatRange(from = 0.0) topRightRadius: Float? = null,
    @FloatRange(from = 0.0) bottomLeftRadius: Float? = null,
    @FloatRange(from = 0.0) bottomRightRadius: Float? = null,
    isBlur: Boolean? = null,
    @FloatRange(from = 0.0, to = 25.0, fromInclusive = true) blurRadius: Float? = null,
    isGrayscale: Boolean? = null,
    @ColorInt filterColor: Int? = null,
    lifecycleOwner: LifecycleOwner? = null,
) {
    load(data = imgData) {
        crossfade(200)
        // 生命周期
        lifecycle(lifecycleOwner)
        // 可选的，但是设置 ViewSizeResolver 可以通过限制预加载的大小来节省内存
        size(ViewSizeResolver(this@loadData))
        // 占位图
        placeholderResId?.let {
            placeholder(it)
        } ?: placeholderDrawable?.let {
            placeholder(it)
        }
        // 错误图
        errorResId?.let {
            error(it)
        } ?: errorDrawable?.let {
            error(it)
        }

        val transformations = arrayListOf<Transformation>()
        if (centerCrop == true) {
            transformations.add(CropTransformation(CropTransformation.CropType.CENTER))
        }
        when {
            isCircle == true -> {
                transformations.add(CircleCropTransformation())
            }
            roundedCornerRadius != null -> {
                transformations.add(
                    RoundedCornersTransformation(
                        topLeft = roundedCornerRadius.Dp,
                        topRight = roundedCornerRadius.Dp,
                        bottomLeft = roundedCornerRadius.Dp,
                        bottomRight = roundedCornerRadius.Dp
                    )
                )
            }
            topLeftRadius != null || topRightRadius != null
                    || bottomLeftRadius != null || bottomRightRadius != null -> {
                transformations.add(
                    RoundedCornersTransformation(
                        topLeft = topLeftRadius?.Dp ?: 0f,
                        topRight = topRightRadius?.Dp ?: 0f,
                        bottomLeft = bottomLeftRadius?.Dp ?: 0f,
                        bottomRight = bottomRightRadius?.Dp ?: 0f
                    )
                )
            }
        }
        if (isBlur == true) {
            // 高斯模糊
            transformations.add(BlurTransformation(context, blurRadius ?: 20f))
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