package com.fridayof1995.tabanimation

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.Resources
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.smart_tabs_view.view.*


/**
 * Created by Depression on 11-08-2018.
 */
class AnimatedTabLayout
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {


    val mArgbEvaluator: ArgbEvaluator = ArgbEvaluator()
    val mCenterColor: Int = ContextCompat.getColor(context, android.R.color.white)
    val mSideColor: Int = ContextCompat.getColor(context, R.color.colorSideTab)
    var centerPadding: Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
            , 80f, resources.displayMetrics).toInt()

    var expandedAt: Int = 0
    var mEndViewsTranslationX: Int? = null
    var mCenterTranslationY: Int? = null

    private var icons: ArrayList<Int> = getDefaultIcons()

    init {
        init()
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.VPAnimatedTabLayout,
                0, 0)

        try {
            expandedAt = a.getInteger(R.styleable.VPAnimatedTabLayout_expandedAt, 0)
            if (expandedAt < 2) {
                centerPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                        , 44f, resources.displayMetrics).toInt()
            }
        } finally {
            a.recycle()
        }
    }


    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.smart_tabs_view, this, true)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.e("onPageScrolled", "position $position positionOffset$positionOffset")
        if (position == expandedAt - 1) expandTabs(positionOffset) // expand
        else if (position == expandedAt) collapseTabs(positionOffset) // collapse

    }


    private fun collapseTabs(positionOffset: Float) {
        // collapses when position ==  1
        setTabChangingColor(positionOffset)
        mIndicator.translationX = (positionOffset * centerPadding * 1.9f)
    }

    private fun expandTabs(positionOffset: Float) {
        // expands when position == 0
        setTabChangingColor(1 - positionOffset)
        mIndicator.translationX = ((positionOffset - 1) * centerPadding * 1.9f)
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(position: Int) {
    }


    fun setupWithViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(this)
        viewPager.setPageTransformer(true, StackTransformer())
        bottom_center.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mEndViewsTranslationX = (bottom_center.x - start.x - centerPadding).toInt()
                mCenterTranslationY = height - bottom_center.bottom
                bottom_center.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                viewPager.currentItem = expandedAt
            }
        })

        center.setOnClickListener() {
            if (viewPager.currentItem != 1) {
                viewPager.setCurrentItem(1)

//                val centreX = center.x
//                mIndicator.x = centreX
            }
        }
        start.setOnClickListener() {
            if (viewPager.currentItem != 0) {
                viewPager.setCurrentItem(0)

//                val centreX = start.x
//                mIndicator.x = centreX

            }
        }
        end.setOnClickListener() {
            if (viewPager.currentItem != 2) {
                viewPager.setCurrentItem(2)

//                val centreX = end.x
//                mIndicator.x = centreX
            }
        }
    }


    fun setIcons(@DrawableRes largeCenterIcon: Int = R.drawable.ic_ring,
                 @DrawableRes smallCenterIcon: Int = R.drawable.ic_view_white,
                 @DrawableRes startIcon: Int = R.drawable.ic_comment_white,
                 @DrawableRes endIcon: Int = R.drawable.ic_white_whatshot) {
        center.setImageResource(largeCenterIcon)
        bottom_center.setImageResource(smallCenterIcon)
        start.setImageResource(startIcon)
        end.setImageResource(endIcon)
    }

    fun getDefaultIcons(): ArrayList<Int> {
        var icons: ArrayList<Int> = ArrayList<Int>()
        icons.add(R.drawable.ic_ring)
        icons.add(R.drawable.ic_view_white)
        icons.add(R.drawable.ic_comment_white)
        icons.add(R.drawable.ic_white_whatshot)
        return icons
    }


    private fun moveAndScaleCenter(fractionFromCenter: Float) {

        val centerTranslationY: Float? = mCenterTranslationY?.times(fractionFromCenter)

        if (centerTranslationY != null) {
            center.translationY = centerTranslationY * 3.5f
            bottom_center.translationY = centerTranslationY * 3.5f
        }


        val centerScale: Float = 1 - fractionFromCenter
        Log.e("onPageSelected", "position  $centerScale fractionFromCenter  $fractionFromCenter ")

        if (centerScale > 0.8f) {
            center.scaleX = centerScale
            center.scaleY = centerScale
        }
        bottom_center.alpha =  centerScale
        bottom_center.scaleY = centerScale
        bottom_center.scaleX = centerScale
    }

    private fun moveViews(fractionFromCenter: Float) {

        val translation: Float = mEndViewsTranslationX?.times(fractionFromCenter)
                ?: centerPadding.toFloat()

        start.translationX = translation
        end.translationX = -translation
    }


    fun setTabChangingColor(fractionFromCenter: Float) {
        //    val color = mArgbEvaluator.evaluate(fractionFromCenter, mCenterColor, mSideColor) as Int

//        center.setColorFilter(color)
//        bottom_center.setColorFilter(color)
//        start.setColorFilter(color)
//        end.setColorFilter(color)

        mIndicator.alpha = fractionFromCenter
        mIndicator.scaleX = fractionFromCenter
//        Log.e("setTabChangingColor", "fractionFromCenter  $fractionFromCenter")


        transitionBackground.alpha = fractionFromCenter

        moveViews(fractionFromCenter)
        moveAndScaleCenter(fractionFromCenter)
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    enum class Color(val value: Int) {
        RED(0),
        GREEN(1),
        BLUE(2)
    }
}
