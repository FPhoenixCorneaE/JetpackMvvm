package com.fphoenixcorneae.jetpackmvvm.databinding

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.loadAny
import coil.size.ViewSizeResolver
import coil.transform.*
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.fphoenixcorneae.ext.isNotNull
import com.fphoenixcorneae.ext.view.setTintColor

/**
 * @desc：@BindingAdapter 自定义属性
 * @date：2021-04-11-22:33
 */
@BindingAdapter("selected")
fun View.setSelected(selected: Boolean) {
    isSelected = selected
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    isVisible = visible
}

@BindingAdapter(value = ["onSingleClick"], requireAll = false)
fun View.setOnSingleClick(onClickListener: View.OnClickListener) {
    val hits = LongArray(2)
    setOnClickListener {
        System.arraycopy(hits, 1, hits, 0, hits.size - 1)
        hits[hits.size - 1] = SystemClock.uptimeMillis()
        if (hits[0] < SystemClock.uptimeMillis() - 500) {
            onClickListener.onClick(it)
        }
    }
}

@BindingAdapter(value = ["onSingleClick"], requireAll = false)
fun View.setOnSingleClick(block: () -> Unit) {
    val hits = LongArray(2)
    setOnClickListener {
        System.arraycopy(hits, 1, hits, 0, hits.size - 1)
        hits[hits.size - 1] = SystemClock.uptimeMillis()
        if (hits[0] < SystemClock.uptimeMillis() - 500) {
            block()
        }
    }
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

/**
 * 添加分割线
 */
@BindingAdapter(
    value = [
        "strikeThruText",
    ], requireAll = false
)
fun TextView.setStrikeThruText(enable: Boolean) {
    // 添加删除线
    paintFlags = if (enable) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter(
    value = [
        "adapter",
        "userInputEnabled",
        "offscreenPageLimit"
    ], requireAll = false
)
fun ViewPager2.init(
    adapter: RecyclerView.Adapter<*>,
    userInputEnabled: Boolean = true,
    @IntRange(from = 1) offscreenPageLimit: Int = 1,
) {
    this.adapter = adapter
    isUserInputEnabled = userInputEnabled
    setOffscreenPageLimit(offscreenPageLimit)
}

@BindingAdapter(
    value = [
        "adapter",
        "layoutManager"
    ], requireAll = false
)
fun RecyclerView.init(
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context),
) {
    this.adapter = adapter
    this.layoutManager = layoutManager
}