package com.fridayof1995.tabanimation

import androidx.viewpager.widget.ViewPager
import android.view.View


/**
 * Created by Depression on 12-08-2018.
 */
class StackTransformer : androidx.viewpager.widget.ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.pivotX = if (position < 0) (view.width).toFloat() else 1f
        view.scaleX = if (position < 0) 1f   else 1f
    }
}