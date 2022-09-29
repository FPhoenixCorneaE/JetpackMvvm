package com.fphoenixcorneae.jetpackmvvm.databinding

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import com.fphoenixcorneae.common.ext.Dp
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import kotlin.math.roundToInt

/**
 * ShapeableImageView 属性设置
 * @param cornerFamily            the family to use to create the corner treatment
 * @param cornerSize              the size to use for the corner
 * @param topLeftCornerFamily     the family to use to create the corner treatment for the top left corner
 * @param topLeftCornerSize       the size to use for the top left corner
 * @param topRightCornerFamily    the family to use to create the corner treatment for the top right corner
 * @param topRightCornerSize      the size to use for the top right corner
 * @param bottomLeftCornerFamily  the family to use to create the corner treatment for the bottom left corner
 * @param bottomLeftCornerSize    the size to use for the bottom left corner
 * @param bottomRightCornerFamily the family to use to create the corner treatment for the bottom right corner
 * @param bottomRightCornerSize   the size to use for the bottom right corner
 * @param borderColorStateList    the color state list for border
 * @param borderColor             the color for border
 * @param borderSize              the size for border
 */
@BindingAdapter(value = [
    "cornerFamily", "cornerSize", "topLeftCornerFamily", "topLeftCornerSize", "topRightCornerFamily", "topRightCornerSize",
    "bottomLeftCornerFamily", "bottomLeftCornerSize", "bottomRightCornerFamily", "bottomRightCornerSize",
    "borderColorStateList", "borderColor", "borderSize",
], requireAll = false)
fun ShapeableImageView.setAttrs(
    @CornerFamily cornerFamily: Int?,
    @Dimension(unit = Dimension.DP) cornerSize: Float?,
    @CornerFamily topLeftCornerFamily: Int?,
    @Dimension(unit = Dimension.DP) topLeftCornerSize: Float?,
    @CornerFamily topRightCornerFamily: Int?,
    @Dimension(unit = Dimension.DP) topRightCornerSize: Float?,
    @CornerFamily bottomLeftCornerFamily: Int?,
    @Dimension(unit = Dimension.DP) bottomLeftCornerSize: Float?,
    @CornerFamily bottomRightCornerFamily: Int?,
    @Dimension(unit = Dimension.DP) bottomRightCornerSize: Float?,
    borderColorStateList: ColorStateList?,
    @ColorInt borderColor: Int?,
    @Dimension(unit = Dimension.DP) borderSize: Float?,
) {
    shapeAppearanceModel = ShapeAppearanceModel.builder().apply {
        setTopLeftCorner(
            cornerFamily ?: topLeftCornerFamily ?: CornerFamily.ROUNDED,
            cornerSize?.Dp ?: topLeftCornerSize?.Dp ?: 0f,
        )
        setTopRightCorner(
            cornerFamily ?: topRightCornerFamily ?: CornerFamily.ROUNDED,
            cornerSize?.Dp ?: topRightCornerSize?.Dp ?: 0f,
        )
        setBottomLeftCorner(
            cornerFamily ?: bottomLeftCornerFamily ?: CornerFamily.ROUNDED,
            cornerSize?.Dp ?: bottomLeftCornerSize?.Dp ?: 0f,
        )
        setBottomRightCorner(
            cornerFamily ?: bottomRightCornerFamily ?: CornerFamily.ROUNDED,
            cornerSize?.Dp ?: bottomRightCornerSize?.Dp ?: 0f,
        )
    }.build()
    borderColorStateList?.let { strokeColor = it } ?: borderColor?.let { strokeColor = ColorStateList.valueOf(it) }
    borderSize?.let { strokeWidth = it.Dp.also { setPadding(it.roundToInt()) } }
}