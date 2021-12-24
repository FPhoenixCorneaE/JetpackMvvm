package com.fphoenixcorneae.jetpackmvvm.databinding.drawable

import android.R
import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter

/**
 * @desc：使用DataBinding时强烈推荐该库 https://github.com/whataa/noDrawable
 * 只需要复制核心类 ProxyDrawable，Drawables至项目中即可
 * 可以减少大量的drawable.xml文件，很香
 * @date：2021/12/24 10:57
 */
object Drawables {
    private const val INVALID = 0
    private val tmpPadding = IntArray(4)

    /**
     * normal, checked, checkable, enabled, focused, pressed, selected
     */
    @BindingAdapter(
        value = [
            "drawable_shapeMode",
            "drawable_solidColor",
            "drawable_strokeColor",
            "drawable_strokeWidth",
            "drawable_strokeDash",
            "drawable_strokeDashGap",
            "drawable_radius",
            "drawable_radiusLT",
            "drawable_radiusLB",
            "drawable_radiusRT",
            "drawable_radiusRB",
            "drawable_startColor",
            "drawable_centerColor",
            "drawable_endColor",
            "drawable_orientation",
            "drawable_gradientType",
            "drawable_radialCenterX",
            "drawable_radialCenterY",
            "drawable_radialRadius",
            "drawable_width",
            "drawable_height",
            "drawable_marginLeft",
            "drawable_marginTop",
            "drawable_marginRight",
            "drawable_marginBottom",
            "drawable_ringThickness",
            "drawable_ringThicknessRatio",
            "drawable_ringInnerRadius",
            "drawable_ringInnerRadiusRatio",
            "drawable_checked_shapeMode",
            "drawable_checked_solidColor",
            "drawable_checked_strokeColor",
            "drawable_checked_strokeWidth",
            "drawable_checked_strokeDash",
            "drawable_checked_strokeDashGap",
            "drawable_checked_radius",
            "drawable_checked_radiusLT",
            "drawable_checked_radiusLB",
            "drawable_checked_radiusRT",
            "drawable_checked_radiusRB",
            "drawable_checked_startColor",
            "drawable_checked_centerColor",
            "drawable_checked_endColor",
            "drawable_checked_orientation",
            "drawable_checked_gradientType",
            "drawable_checked_radialCenterX",
            "drawable_checked_radialCenterY",
            "drawable_checked_radialRadius",
            "drawable_checked_width",
            "drawable_checked_height",
            "drawable_checked_marginLeft",
            "drawable_checked_marginTop",
            "drawable_checked_marginRight",
            "drawable_checked_marginBottom",
            "drawable_checked_ringThickness",
            "drawable_checked_ringThicknessRatio",
            "drawable_checked_ringInnerRadius",
            "drawable_checked_ringInnerRadiusRatio",
            "drawable_checkable_shapeMode",
            "drawable_checkable_solidColor",
            "drawable_checkable_strokeColor",
            "drawable_checkable_strokeWidth",
            "drawable_checkable_strokeDash",
            "drawable_checkable_strokeDashGap",
            "drawable_checkable_radius",
            "drawable_checkable_radiusLT",
            "drawable_checkable_radiusLB",
            "drawable_checkable_radiusRT",
            "drawable_checkable_radiusRB",
            "drawable_checkable_startColor",
            "drawable_checkable_centerColor",
            "drawable_checkable_endColor",
            "drawable_checkable_orientation",
            "drawable_checkable_gradientType",
            "drawable_checkable_radialCenterX",
            "drawable_checkable_radialCenterY",
            "drawable_checkable_radialRadius",
            "drawable_checkable_width",
            "drawable_checkable_height",
            "drawable_checkable_marginLeft",
            "drawable_checkable_marginTop",
            "drawable_checkable_marginRight",
            "drawable_checkable_marginBottom",
            "drawable_checkable_ringThickness",
            "drawable_checkable_ringThicknessRatio",
            "drawable_checkable_ringInnerRadius",
            "drawable_checkable_ringInnerRadiusRatio",
            "drawable_enabled_shapeMode",
            "drawable_enabled_solidColor",
            "drawable_enabled_strokeColor",
            "drawable_enabled_strokeWidth",
            "drawable_enabled_strokeDash",
            "drawable_enabled_strokeDashGap",
            "drawable_enabled_radius",
            "drawable_enabled_radiusLT",
            "drawable_enabled_radiusLB",
            "drawable_enabled_radiusRT",
            "drawable_enabled_radiusRB",
            "drawable_enabled_startColor",
            "drawable_enabled_centerColor",
            "drawable_enabled_endColor",
            "drawable_enabled_orientation",
            "drawable_enabled_gradientType",
            "drawable_enabled_radialCenterX",
            "drawable_enabled_radialCenterY",
            "drawable_enabled_radialRadius",
            "drawable_enabled_width",
            "drawable_enabled_height",
            "drawable_enabled_marginLeft",
            "drawable_enabled_marginTop",
            "drawable_enabled_marginRight",
            "drawable_enabled_marginBottom",
            "drawable_enabled_ringThickness",
            "drawable_enabled_ringThicknessRatio",
            "drawable_enabled_ringInnerRadius",
            "drawable_enabled_ringInnerRadiusRatio",
            "drawable_focused_shapeMode",
            "drawable_focused_solidColor",
            "drawable_focused_strokeColor",
            "drawable_focused_strokeWidth",
            "drawable_focused_strokeDash",
            "drawable_focused_strokeDashGap",
            "drawable_focused_radius",
            "drawable_focused_radiusLT",
            "drawable_focused_radiusLB",
            "drawable_focused_radiusRT",
            "drawable_focused_radiusRB",
            "drawable_focused_startColor",
            "drawable_focused_centerColor",
            "drawable_focused_endColor",
            "drawable_focused_orientation",
            "drawable_focused_gradientType",
            "drawable_focused_radialCenterX",
            "drawable_focused_radialCenterY",
            "drawable_focused_radialRadius",
            "drawable_focused_width",
            "drawable_focused_height",
            "drawable_focused_marginLeft",
            "drawable_focused_marginTop",
            "drawable_focused_marginRight",
            "drawable_focused_marginBottom",
            "drawable_focused_ringThickness",
            "drawable_focused_ringThicknessRatio",
            "drawable_focused_ringInnerRadius",
            "drawable_focused_ringInnerRadiusRatio",
            "drawable_pressed_shapeMode",
            "drawable_pressed_solidColor",
            "drawable_pressed_strokeColor",
            "drawable_pressed_strokeWidth",
            "drawable_pressed_strokeDash",
            "drawable_pressed_strokeDashGap",
            "drawable_pressed_radius",
            "drawable_pressed_radiusLT",
            "drawable_pressed_radiusLB",
            "drawable_pressed_radiusRT",
            "drawable_pressed_radiusRB",
            "drawable_pressed_startColor",
            "drawable_pressed_centerColor",
            "drawable_pressed_endColor",
            "drawable_pressed_orientation",
            "drawable_pressed_gradientType",
            "drawable_pressed_radialCenterX",
            "drawable_pressed_radialCenterY",
            "drawable_pressed_radialRadius",
            "drawable_pressed_width",
            "drawable_pressed_height",
            "drawable_pressed_marginLeft",
            "drawable_pressed_marginTop",
            "drawable_pressed_marginRight",
            "drawable_pressed_marginBottom",
            "drawable_pressed_ringThickness",
            "drawable_pressed_ringThicknessRatio",
            "drawable_pressed_ringInnerRadius",
            "drawable_pressed_ringInnerRadiusRatio",
            "drawable_selected_shapeMode",
            "drawable_selected_solidColor",
            "drawable_selected_strokeColor",
            "drawable_selected_strokeWidth",
            "drawable_selected_strokeDash",
            "drawable_selected_strokeDashGap",
            "drawable_selected_radius",
            "drawable_selected_radiusLT",
            "drawable_selected_radiusLB",
            "drawable_selected_radiusRT",
            "drawable_selected_radiusRB",
            "drawable_selected_startColor",
            "drawable_selected_centerColor",
            "drawable_selected_endColor",
            "drawable_selected_orientation",
            "drawable_selected_gradientType",
            "drawable_selected_radialCenterX",
            "drawable_selected_radialCenterY",
            "drawable_selected_radialRadius",
            "drawable_selected_width",
            "drawable_selected_height",
            "drawable_selected_marginLeft",
            "drawable_selected_marginTop",
            "drawable_selected_marginRight",
            "drawable_selected_marginBottom",
            "drawable_selected_ringThickness",
            "drawable_selected_ringThicknessRatio",
            "drawable_selected_ringInnerRadius",
            "drawable_selected_ringInnerRadiusRatio",
            // normal, checked, checkable, enabled, focused, pressed, selected
            "drawable",
            "drawable_checked",
            "drawable_checkable",
            "drawable_enabled",
            "drawable_focused",
            "drawable_pressed",
            "drawable_selected"
        ],
        requireAll = false
    )
    fun setViewBackground(
        view: View,
        @ShapeMode shapeMode: Int,
        @ColorInt solidColor: Int,
        @ColorInt strokeColor: Int,
        strokeWidth: Float,
        strokeDash: Float,
        strokeDashGap: Float,
        radius: Float,
        radiusLT: Float,
        radiusLB: Float,
        radiusRT: Float,
        radiusRB: Float,
        @ColorInt startColor: Int,
        @ColorInt centerColor: Int,
        @ColorInt endColor: Int,
        @Orientation orientation: Int,
        @GradientType gradientType: Int,
        radialCenterX: Float?,
        radialCenterY: Float?,
        radialRadius: Float,
        width: Float,
        height: Float,
        marginLeft: Float,
        marginTop: Float,
        marginRight: Float,
        marginBottom: Float,
        ringThickness: Float,
        ringThicknessRatio: Float,
        ringInnerRadius: Float,
        ringInnerRadiusRatio: Float,
        @ShapeMode checked_shapeMode: Int,
        @ColorInt checked_solidColor: Int,
        @ColorInt checked_strokeColor: Int,
        checked_strokeWidth: Float,
        checked_strokeDash: Float,
        checked_strokeDashGap: Float,
        checked_radius: Float,
        checked_radiusLT: Float,
        checked_radiusLB: Float,
        checked_radiusRT: Float,
        checked_radiusRB: Float,
        @ColorInt checked_startColor: Int,
        @ColorInt checked_centerColor: Int,
        @ColorInt checked_endColor: Int,
        @Orientation checked_orientation: Int,
        @GradientType checked_gradientType: Int,
        checked_radialCenterX: Float?,
        checked_radialCenterY: Float?,
        checked_radialRadius: Float,
        checked_width: Float,
        checked_height: Float,
        checked_marginLeft: Float,
        checked_marginTop: Float,
        checked_marginRight: Float,
        checked_marginBottom: Float,
        checked_ringThickness: Float,
        checked_ringThicknessRatio: Float,
        checked_ringInnerRadius: Float,
        checked_ringInnerRadiusRatio: Float,
        @ShapeMode checkable_shapeMode: Int,
        @ColorInt checkable_solidColor: Int,
        @ColorInt checkable_strokeColor: Int,
        checkable_strokeWidth: Float,
        checkable_strokeDash: Float,
        checkable_strokeDashGap: Float,
        checkable_radius: Float,
        checkable_radiusLT: Float,
        checkable_radiusLB: Float,
        checkable_radiusRT: Float,
        checkable_radiusRB: Float,
        @ColorInt checkable_startColor: Int,
        @ColorInt checkable_centerColor: Int,
        @ColorInt checkable_endColor: Int,
        @Orientation checkable_orientation: Int,
        @GradientType checkable_gradientType: Int,
        checkable_radialCenterX: Float?,
        checkable_radialCenterY: Float?,
        checkable_radialRadius: Float,
        checkable_width: Float,
        checkable_height: Float,
        checkable_marginLeft: Float,
        checkable_marginTop: Float,
        checkable_marginRight: Float,
        checkable_marginBottom: Float,
        checkable_ringThickness: Float,
        checkable_ringThicknessRatio: Float,
        checkable_ringInnerRadius: Float,
        checkable_ringInnerRadiusRatio: Float,
        @ShapeMode enabled_shapeMode: Int,
        @ColorInt enabled_solidColor: Int,
        @ColorInt enabled_strokeColor: Int,
        enabled_strokeWidth: Float,
        enabled_strokeDash: Float,
        enabled_strokeDashGap: Float,
        enabled_radius: Float,
        enabled_radiusLT: Float,
        enabled_radiusLB: Float,
        enabled_radiusRT: Float,
        enabled_radiusRB: Float,
        @ColorInt enabled_startColor: Int,
        @ColorInt enabled_centerColor: Int,
        @ColorInt enabled_endColor: Int,
        @Orientation enabled_orientation: Int,
        @GradientType enabled_gradientType: Int,
        enabled_radialCenterX: Float?,
        enabled_radialCenterY: Float?,
        enabled_radialRadius: Float,
        enabled_width: Float,
        enabled_height: Float,
        enabled_marginLeft: Float,
        enabled_marginTop: Float,
        enabled_marginRight: Float,
        enabled_marginBottom: Float,
        enabled_ringThickness: Float,
        enabled_ringThicknessRatio: Float,
        enabled_ringInnerRadius: Float,
        enabled_ringInnerRadiusRatio: Float,
        @ShapeMode focused_shapeMode: Int,
        @ColorInt focused_solidColor: Int,
        @ColorInt focused_strokeColor: Int,
        focused_strokeWidth: Float,
        focused_strokeDash: Float,
        focused_strokeDashGap: Float,
        focused_radius: Float,
        focused_radiusLT: Float,
        focused_radiusLB: Float,
        focused_radiusRT: Float,
        focused_radiusRB: Float,
        @ColorInt focused_startColor: Int,
        @ColorInt focused_centerColor: Int,
        @ColorInt focused_endColor: Int,
        @Orientation focused_orientation: Int,
        @GradientType focused_gradientType: Int,
        focused_radialCenterX: Float?,
        focused_radialCenterY: Float?,
        focused_radialRadius: Float,
        focused_width: Float,
        focused_height: Float,
        focused_marginLeft: Float,
        focused_marginTop: Float,
        focused_marginRight: Float,
        focused_marginBottom: Float,
        focused_ringThickness: Float,
        focused_ringThicknessRatio: Float,
        focused_ringInnerRadius: Float,
        focused_ringInnerRadiusRatio: Float,
        @ShapeMode pressed_shapeMode: Int,
        @ColorInt pressed_solidColor: Int,
        @ColorInt pressed_strokeColor: Int,
        pressed_strokeWidth: Float,
        pressed_strokeDash: Float,
        pressed_strokeDashGap: Float,
        pressed_radius: Float,
        pressed_radiusLT: Float,
        pressed_radiusLB: Float,
        pressed_radiusRT: Float,
        pressed_radiusRB: Float,
        @ColorInt pressed_startColor: Int,
        @ColorInt pressed_centerColor: Int,
        @ColorInt pressed_endColor: Int,
        @Orientation pressed_orientation: Int,
        @GradientType pressed_gradientType: Int,
        pressed_radialCenterX: Float?,
        pressed_radialCenterY: Float?,
        pressed_radialRadius: Float,
        pressed_width: Float,
        pressed_height: Float,
        pressed_marginLeft: Float,
        pressed_marginTop: Float,
        pressed_marginRight: Float,
        pressed_marginBottom: Float,
        pressed_ringThickness: Float,
        pressed_ringThicknessRatio: Float,
        pressed_ringInnerRadius: Float,
        pressed_ringInnerRadiusRatio: Float,
        @ShapeMode selected_shapeMode: Int,
        @ColorInt selected_solidColor: Int,
        @ColorInt selected_strokeColor: Int,
        selected_strokeWidth: Float,
        selected_strokeDash: Float,
        selected_strokeDashGap: Float,
        selected_radius: Float,
        selected_radiusLT: Float,
        selected_radiusLB: Float,
        selected_radiusRT: Float,
        selected_radiusRB: Float,
        @ColorInt selected_startColor: Int,
        @ColorInt selected_centerColor: Int,
        @ColorInt selected_endColor: Int,
        @Orientation selected_orientation: Int,
        @GradientType selected_gradientType: Int,
        selected_radialCenterX: Float?,
        selected_radialCenterY: Float?,
        selected_radialRadius: Float,
        selected_width: Float,
        selected_height: Float,
        selected_marginLeft: Float,
        selected_marginTop: Float,
        selected_marginRight: Float,
        selected_marginBottom: Float,
        selected_ringThickness: Float,
        selected_ringThicknessRatio: Float,
        selected_ringInnerRadius: Float,
        selected_ringInnerRadiusRatio: Float,
        drawable: Drawable?,
        drawable_checked: Drawable?,
        drawable_checkable: Drawable?,
        drawable_enabled: Drawable?,
        drawable_focused: Drawable?,
        drawable_pressed: Drawable?,
        drawable_selected: Drawable?
    ) {
        var isDefaultNull = false
        var count = 0
        val defaultDrawable = drawable
            ?: create(
                shapeMode,
                solidColor,
                strokeColor,
                strokeWidth,
                strokeDash,
                strokeDashGap,
                radius,
                radiusLT,
                radiusLB,
                radiusRT,
                radiusRB,
                startColor,
                centerColor,
                endColor,
                orientation,
                gradientType,
                radialCenterX,
                radialCenterY,
                radialRadius,
                width,
                height,
                marginLeft,
                marginTop,
                marginRight,
                marginBottom,
                ringThickness,
                ringThicknessRatio,
                ringInnerRadius,
                ringInnerRadiusRatio
            )
        if (defaultDrawable != null) {
            count++
        } else {
            isDefaultNull = true
        }
        val checkedDrawable = drawable_checked
            ?: create(
                checked_shapeMode,
                checked_solidColor,
                checked_strokeColor,
                checked_strokeWidth,
                checked_strokeDash,
                checked_strokeDashGap,
                checked_radius,
                checked_radiusLT,
                checked_radiusLB,
                checked_radiusRT,
                checked_radiusRB,
                checked_startColor,
                checked_centerColor,
                checked_endColor,
                checked_orientation,
                checked_gradientType,
                checked_radialCenterX,
                checked_radialCenterY,
                checked_radialRadius,
                checked_width,
                checked_height,
                checked_marginLeft,
                checked_marginTop,
                checked_marginRight,
                checked_marginBottom,
                checked_ringThickness,
                checked_ringThicknessRatio,
                checked_ringInnerRadius,
                checked_ringInnerRadiusRatio
            )
        if (checkedDrawable != null) {
            count++
        }
        val checkableDrawable = drawable_checkable
            ?: create(
                checkable_shapeMode,
                checkable_solidColor,
                checkable_strokeColor,
                checkable_strokeWidth,
                checkable_strokeDash,
                checkable_strokeDashGap,
                checkable_radius,
                checkable_radiusLT,
                checkable_radiusLB,
                checkable_radiusRT,
                checkable_radiusRB,
                checkable_startColor,
                checkable_centerColor,
                checkable_endColor,
                checkable_orientation,
                checkable_gradientType,
                checkable_radialCenterX,
                checkable_radialCenterY,
                checkable_radialRadius,
                checkable_width,
                checkable_height,
                checkable_marginLeft,
                checkable_marginTop,
                checkable_marginRight,
                checkable_marginBottom,
                checkable_ringThickness,
                checkable_ringThicknessRatio,
                checkable_ringInnerRadius,
                checkable_ringInnerRadiusRatio
            )
        if (checkableDrawable != null) {
            count++
        }
        val enabledDrawable = drawable_enabled
            ?: create(
                enabled_shapeMode,
                enabled_solidColor,
                enabled_strokeColor,
                enabled_strokeWidth,
                enabled_strokeDash,
                enabled_strokeDashGap,
                enabled_radius,
                enabled_radiusLT,
                enabled_radiusLB,
                enabled_radiusRT,
                enabled_radiusRB,
                enabled_startColor,
                enabled_centerColor,
                enabled_endColor,
                enabled_orientation,
                enabled_gradientType,
                enabled_radialCenterX,
                enabled_radialCenterY,
                enabled_radialRadius,
                enabled_width,
                enabled_height,
                enabled_marginLeft,
                enabled_marginTop,
                enabled_marginRight,
                enabled_marginBottom,
                enabled_ringThickness,
                enabled_ringThicknessRatio,
                enabled_ringInnerRadius,
                enabled_ringInnerRadiusRatio
            )
        if (enabledDrawable != null) {
            count++
        }
        val focusedDrawable = drawable_focused
            ?: create(
                focused_shapeMode,
                focused_solidColor,
                focused_strokeColor,
                focused_strokeWidth,
                focused_strokeDash,
                focused_strokeDashGap,
                focused_radius,
                focused_radiusLT,
                focused_radiusLB,
                focused_radiusRT,
                focused_radiusRB,
                focused_startColor,
                focused_centerColor,
                focused_endColor,
                focused_orientation,
                focused_gradientType,
                focused_radialCenterX,
                focused_radialCenterY,
                focused_radialRadius,
                focused_width,
                focused_height,
                focused_marginLeft,
                focused_marginTop,
                focused_marginRight,
                focused_marginBottom,
                focused_ringThickness,
                focused_ringThicknessRatio,
                focused_ringInnerRadius,
                focused_ringInnerRadiusRatio
            )
        if (focusedDrawable != null) {
            count++
        }
        val pressedDrawable = drawable_pressed
            ?: create(
                pressed_shapeMode,
                pressed_solidColor,
                pressed_strokeColor,
                pressed_strokeWidth,
                pressed_strokeDash,
                pressed_strokeDashGap,
                pressed_radius,
                pressed_radiusLT,
                pressed_radiusLB,
                pressed_radiusRT,
                pressed_radiusRB,
                pressed_startColor,
                pressed_centerColor,
                pressed_endColor,
                pressed_orientation,
                pressed_gradientType,
                pressed_radialCenterX,
                pressed_radialCenterY,
                pressed_radialRadius,
                pressed_width,
                pressed_height,
                pressed_marginLeft,
                pressed_marginTop,
                pressed_marginRight,
                pressed_marginBottom,
                pressed_ringThickness,
                pressed_ringThicknessRatio,
                pressed_ringInnerRadius,
                pressed_ringInnerRadiusRatio
            )
        if (pressedDrawable != null) {
            count++
        }
        val selectedDrawable = drawable_selected
            ?: create(
                selected_shapeMode,
                selected_solidColor,
                selected_strokeColor,
                selected_strokeWidth,
                selected_strokeDash,
                selected_strokeDashGap,
                selected_radius,
                selected_radiusLT,
                selected_radiusLB,
                selected_radiusRT,
                selected_radiusRB,
                selected_startColor,
                selected_centerColor,
                selected_endColor,
                selected_orientation,
                selected_gradientType,
                selected_radialCenterX,
                selected_radialCenterY,
                selected_radialRadius,
                selected_width,
                selected_height,
                selected_marginLeft,
                selected_marginTop,
                selected_marginRight,
                selected_marginBottom,
                selected_ringThickness,
                selected_ringThicknessRatio,
                selected_ringInnerRadius,
                selected_ringInnerRadiusRatio
            )
        if (selectedDrawable != null) {
            count++
        }
        if (count < 1) {
            // impossible，因为该方法被调用说明至少声明了一条属性
        } else {
            var needReSetPadding = false
            if (isDefaultNull || count == 1) {
                // 当设置了margin（非view的margin）时，InsetDrawable会导致view本身的padding失效
                needReSetPadding = true
                tmpPadding[0] = view.paddingLeft
                tmpPadding[1] = view.paddingTop
                tmpPadding[2] = view.paddingRight
                tmpPadding[3] = view.paddingBottom
            }
            if (count == 1 && !isDefaultNull) {
                view.background = defaultDrawable
            } else {
                val listDrawable = ProxyDrawable()
                if (checkedDrawable != null) {
                    listDrawable.addState(intArrayOf(R.attr.state_checked), checkedDrawable)
                }
                if (checkableDrawable != null) {
                    listDrawable.addState(intArrayOf(R.attr.state_checkable), checkableDrawable)
                }
                if (focusedDrawable != null) {
                    listDrawable.addState(intArrayOf(R.attr.state_focused), focusedDrawable)
                }
                if (pressedDrawable != null) {
                    listDrawable.addState(intArrayOf(R.attr.state_pressed), pressedDrawable)
                }
                if (selectedDrawable != null) {
                    listDrawable.addState(intArrayOf(R.attr.state_selected), selectedDrawable)
                }
                if (enabledDrawable != null) {
                    listDrawable.addState(intArrayOf(R.attr.state_enabled), enabledDrawable)
                }
                if (defaultDrawable != null) {
                    listDrawable.addState(intArrayOf(0), defaultDrawable)
                } else {
                    var originDrawable = view.background
                    if (originDrawable != null) {
                        if (originDrawable is ProxyDrawable) {
                            originDrawable = originDrawable.originDrawable
                        }
                        listDrawable.addState(intArrayOf(0), originDrawable)
                    }
                }
                view.background = listDrawable
            }
            if (needReSetPadding) {
                view.setPadding(tmpPadding[0], tmpPadding[1], tmpPadding[2], tmpPadding[3])
            }
        }
    }

    fun create(
        @ShapeMode shapeMode: Int, @ColorInt solidColor: Int,
        @ColorInt strokeColor: Int, @DP strokeWidth: Float, @DP strokeDash: Float, @DP strokeDashGap: Float,
        @DP radius: Float, @DP radiusLT: Float, @DP radiusLB: Float, @DP radiusRT: Float, @DP radiusRB: Float,
        @ColorInt startColor: Int, @ColorInt centerColor: Int, @ColorInt endColor: Int,
        @Orientation orientation: Int, @GradientType gradientType: Int,
        radialCenterX: Float?, radialCenterY: Float?, radialRadius: Float,
        @DP width: Float, @DP height: Float,
        @DP marginLeft: Float, @DP marginTop: Float, @DP marginRight: Float, @DP marginBottom: Float,
        @DP ringThickness: Float,
        @DP ringThicknessRatio: Float,
        @DP ringInnerRadius: Float,
        @DP ringInnerRadiusRatio: Float
    ): Drawable? {
        if (shapeMode == INVALID && solidColor == INVALID && strokeColor == INVALID && strokeWidth == INVALID.toFloat()
            && strokeDash == INVALID.toFloat() && strokeDashGap == INVALID.toFloat() && radius == INVALID.toFloat()
            && radiusLT == INVALID.toFloat() && radiusLB == INVALID.toFloat() && radiusRT == INVALID.toFloat()
            && radiusRB == INVALID.toFloat() && startColor == INVALID && centerColor == INVALID && endColor == INVALID
            && orientation == INVALID && gradientType == INVALID && radialCenterX == null && radialCenterY == null
            && radialRadius == INVALID.toFloat() && width == INVALID.toFloat() && height == INVALID.toFloat()
            && marginLeft == INVALID.toFloat() && marginTop == INVALID.toFloat() && marginRight == INVALID.toFloat()
            && marginBottom == INVALID.toFloat()) {
            // 这里需要判断empty，因为有可能只设置了一个state的drawable，那么其他state的就是empty了
            return null
        }
        val drawable = GradientDrawable()
        if (startColor != INVALID && endColor != INVALID) {
            val colors: IntArray
            if (centerColor != INVALID) {
                colors = IntArray(3)
                colors[0] = startColor
                colors[1] = centerColor
                colors[2] = endColor
            } else {
                colors = IntArray(2)
                colors[0] = startColor
                colors[1] = endColor
            }
            drawable.colors = colors
            drawable.orientation = mapOrientation(orientation)
            drawable.gradientType = gradientType
            if (gradientType == GradientType.RADIAL) {
                drawable.setGradientCenter(
                    radialCenterX ?: .5f,
                    radialCenterY ?: .5f
                )
                drawable.gradientRadius = dip2px(radialRadius).toFloat()
            }
        } else {
            if (solidColor != INVALID) {
                drawable.setColor(solidColor)
            }
        }
        drawable.shape = validShapeMode(shapeMode)
        if (shapeMode == ShapeMode.RING) {
            // 由于GradientDrawable中没有ring相关的公开API，所以使用反射，若对性能有要求，请注意。
            setRingValue(drawable, ringThickness, ringThicknessRatio, ringInnerRadius, ringInnerRadiusRatio)
        }
        if (strokeWidth > 0) {
            drawable.setStroke(
                dip2px(strokeWidth),
                strokeColor,
                dip2px(strokeDash).toFloat(),
                dip2px(strokeDashGap).toFloat()
            )
        }
        if (radius <= 0) {
            val radiusEach = floatArrayOf(
                dip2px(radiusLT).toFloat(),
                dip2px(radiusLT).toFloat(),
                dip2px(radiusRT).toFloat(),
                dip2px(radiusRT).toFloat(),
                dip2px(radiusRB).toFloat(),
                dip2px(radiusRB).toFloat(),
                dip2px(radiusLB).toFloat(),
                dip2px(radiusLB).toFloat()
            )
            drawable.cornerRadii = radiusEach
        } else {
            drawable.cornerRadius = dip2px(radius).toFloat()
        }
        if (width > 0 && height > 0) {
            // https://stackoverflow.com/a/29180660/4698946
            drawable.setSize(dip2px(width), dip2px(height))
        }
        return if (marginLeft != 0f || marginTop != 0f || marginRight != 0f || marginBottom != 0f) {
            InsetDrawable(
                drawable,
                dip2px(marginLeft),
                dip2px(marginTop),
                dip2px(marginRight),
                dip2px(marginBottom)
            )
        } else {
            drawable
        }
    }

    private fun validShapeMode(@ShapeMode shapeMode: Int): Int {
        return if (shapeMode > ShapeMode.RING || shapeMode < ShapeMode.RECTANGLE) GradientDrawable.RECTANGLE else shapeMode
    }

    private fun mapOrientation(@Orientation orientation: Int): GradientDrawable.Orientation {
        when (orientation) {
            Orientation.BL_TR -> return GradientDrawable.Orientation.BL_TR
            Orientation.BOTTOM_TOP -> return GradientDrawable.Orientation.BOTTOM_TOP
            Orientation.BR_TL -> return GradientDrawable.Orientation.BR_TL
            Orientation.LEFT_RIGHT -> return GradientDrawable.Orientation.LEFT_RIGHT
            Orientation.RIGHT_LEFT -> return GradientDrawable.Orientation.RIGHT_LEFT
            Orientation.TL_BR -> return GradientDrawable.Orientation.TL_BR
            Orientation.TOP_BOTTOM -> return GradientDrawable.Orientation.TOP_BOTTOM
            Orientation.TR_BL -> return GradientDrawable.Orientation.TR_BL
        }
        return GradientDrawable.Orientation.TOP_BOTTOM
    }

    @SuppressLint("SoonBlockedPrivateApi")
    private fun setRingValue(
        drawable: GradientDrawable,
        thickness: Float?, thicknessRatio: Float?,
        innerRadius: Float?, innerRadiusRatio: Float?
    ) {
        try {
            val mGradientState = drawable.javaClass.getDeclaredField("mGradientState")
            mGradientState.isAccessible = true
            val mGradientStateClass: Class<*> = mGradientState[drawable].javaClass
            val mUseLevelForShape = mGradientStateClass.getDeclaredField("mUseLevelForShape")
            mUseLevelForShape.isAccessible = true
            mUseLevelForShape.setBoolean(mGradientState[drawable], false)
            if (thickness != null) {
                val mThickness = mGradientStateClass.getDeclaredField("mThickness")
                mThickness.isAccessible = true
                mThickness.setInt(mGradientState[drawable], dip2px(thickness))
            }
            if (thicknessRatio != null) {
                val mThicknessRatio = mGradientStateClass.getDeclaredField("mThicknessRatio")
                mThicknessRatio.isAccessible = true
                mThicknessRatio.setFloat(mGradientState[drawable], dip2px(thicknessRatio).toFloat())
            }
            if (innerRadius != null) {
                val mInnerRadius = mGradientStateClass.getDeclaredField("mInnerRadius")
                mInnerRadius.isAccessible = true
                mInnerRadius.setInt(mGradientState[drawable], dip2px(innerRadius))
            }
            if (innerRadiusRatio != null) {
                val mInnerRadiusRatio = mGradientStateClass.getDeclaredField("mInnerRadiusRatio")
                mInnerRadiusRatio.isAccessible = true
                mInnerRadiusRatio.setFloat(mGradientState[drawable], dip2px(innerRadiusRatio).toFloat())
            }
        } catch (t: NoSuchFieldException) {
            t.printStackTrace()
        } catch (t: IllegalAccessException) {
            t.printStackTrace()
        }
    }

    private fun dip2px(dipValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dipValue * scale + .5f).toInt()
    }

    @Keep
    @IntDef(ShapeMode.RECTANGLE, ShapeMode.OVAL, ShapeMode.LINE, ShapeMode.RING)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ShapeMode {
        companion object {
            const val RECTANGLE = GradientDrawable.RECTANGLE
            const val OVAL = GradientDrawable.OVAL

            /**
             * 画线时，有几点特性必须要知道的：
             * 1. 只能画水平线，画不了竖线；
             * 2. 线的高度是通过stroke的android:width属性设置的；
             * 3. size的android:height属性定义的是整个形状区域的高度；
             * 4. size的height必须大于stroke的width，否则，线无法显示；
             * 5. 线在整个形状区域中是居中显示的；
             * 6. 线左右两边会留有空白间距，线越粗，空白越大；
             * 7. 引用虚线的view需要添加属性android:layerType，值设为"software"，否则显示不了虚线。
             */
            const val LINE = GradientDrawable.LINE
            const val RING = GradientDrawable.RING
        }
    }

    @Keep
    @IntDef(GradientType.LINEAR, GradientType.RADIAL, GradientType.SWEEP)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class GradientType {
        companion object {
            const val LINEAR = 0
            const val RADIAL = 1
            const val SWEEP = 2
        }
    }

    @Keep
    @IntDef(
        Orientation.TOP_BOTTOM,
        Orientation.TR_BL,
        Orientation.RIGHT_LEFT,
        Orientation.BR_TL,
        Orientation.BOTTOM_TOP,
        Orientation.BL_TR,
        Orientation.LEFT_RIGHT,
        Orientation.TL_BR
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Orientation {
        companion object {
            const val TOP_BOTTOM = 0
            const val TR_BL = 1
            const val RIGHT_LEFT = 2
            const val BR_TL = 3
            const val BOTTOM_TOP = 4
            const val BL_TR = 5
            const val LEFT_RIGHT = 6
            const val TL_BR = 7
        }
    }

    @Keep
    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    internal annotation class DP
}