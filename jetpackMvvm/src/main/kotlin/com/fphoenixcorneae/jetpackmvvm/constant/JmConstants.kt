package com.fphoenixcorneae.jetpackmvvm.constant

import android.view.ViewGroup
import android.widget.RelativeLayout
import com.fphoenixcorneae.ext.dp2Px
import com.fphoenixcorneae.jetpackmvvm.R
import com.fphoenixcorneae.toolbar.CommonToolbar
import com.fphoenixcorneae.util.ResourceUtil

object JmConstants {

    object Toolbar {
        val LAYOUT_PARAMS = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        const val LEFT_TYPE = CommonToolbar.TYPE_LEFT_IMAGE_BUTTON
        val LEFT_IMAGE_TINT_COLOR = ResourceUtil.getColor(R.color.jm_color_title_0x222222)
        const val CENTER_TYPE = CommonToolbar.TYPE_CENTER_TEXT_VIEW
        val CENTER_TEXT_COLOR = ResourceUtil.getColor(R.color.jm_color_title_0x222222)
        const val CENTER_TEXT_SIZE = 18f
        const val CENTER_TEXT_IS_FAKE_BOLD = true
        const val SHOW_BOTTOM_LINE = true
        val TOOLBAR_HEIGHT = 44.dp2Px()
        val TOOLBAR_COLOR = ResourceUtil.getColor(R.color.jm_color_toolbar_0xffffff)
        val STATUS_BAR_COLOR = ResourceUtil.getColor(R.color.jm_color_statusBar_0xdddddd)
    }
}