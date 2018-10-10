package com.fridayof1995.tabanimation

import android.support.v4.view.ViewPager
import android.view.View


/**
 * Created by Depression on 12-08-2018.
 */
class StackTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.pivotX = if (position < 0) (view.width).toFloat() else 1f
        view.scaleX = if (position < 0) 1f   else 1f
    }
}