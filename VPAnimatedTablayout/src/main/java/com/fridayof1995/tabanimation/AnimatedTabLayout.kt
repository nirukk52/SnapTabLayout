package com.fridayof1995.tabanimation

import android.animation.ArgbEvaluator
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
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
    val mCenterColor: Int = ContextCompat.getColor(context, android.R.color.holo_purple)
    val mSideColor: Int = ContextCompat.getColor(context, android.R.color.holo_orange_dark)
    var defaultOffsetFromCenter: Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
            , 80f, resources.displayMetrics).toInt()

    var expandedAt: Int = 0
    var numOfTabs: Int = 3
    var mEndViewsTranslationX: Int? = null
    var mMidViewsTranslationX: Int? = null
    var mCenterTranslationY: Int? = null

    private var icons: ArrayList<Int> = getDefaultIcons()

    init {
        init()
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.VPAnimatedTabLayout,
                0, 0)

        try {
            expandedAt = a.getInteger(R.styleable.VPAnimatedTabLayout_expandedAt
                    , 0)
            numOfTabs = a.getInteger(R.styleable.VPAnimatedTabLayout_numOfTabs
                    , 0)
            if (numOfTabs <= 3) {
                mid_start.visibility = View.GONE
                mid_end.visibility = View.GONE
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
        // collapses at position ==  1
        setTabChangingColor(positionOffset)
        //  mIndicator.translationX = (positionOffset * (start.x + start.width - convertDpToPixel(8f, context)))
    }

    private fun expandTabs(positionOffset: Float) {
        // expands at position == 0
        setTabChangingColor(1 - positionOffset)
        //  mIndicator.translationX = ((positionOffset - 1) * (start.x + start.width - convertDpToPixel(8f, context)))
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

                defaultOffsetFromCenter = (center.width / 2)
                if (numOfTabs > 3) {
                    mMidViewsTranslationX = (center.x - mid_start.x + defaultOffsetFromCenter - 40).toInt()
                    mEndViewsTranslationX = (center.x - start.x - mid_start.width + defaultOffsetFromCenter - 40).toInt()
                } else {
                    mMidViewsTranslationX = (center.x - mid_start.x - defaultOffsetFromCenter + 40).toInt()
                    mEndViewsTranslationX = (center.x - start.x - mid_start.width - defaultOffsetFromCenter + 40).toInt()
                }
                mCenterTranslationY = height - bottom_center.bottom

                bottom_center.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                viewPager.currentItem = expandedAt
            }
        })

        center.setOnClickListener() {
            if (viewPager.currentItem != 1) {
                viewPager.currentItem = 1
            }
        }
        start.setOnClickListener() {
            if (viewPager.currentItem != 0) {
                viewPager.currentItem = 0
            }
        }
        end.setOnClickListener() {
            if (viewPager.currentItem != 2) {
                viewPager.currentItem = 2
            }
        }
    }

    private fun moveViews(fractionFromCenter: Float) {

        val endTranslation: Float = mEndViewsTranslationX?.times(fractionFromCenter)
                ?: defaultOffsetFromCenter.toFloat()

        val midTranslation: Float = mMidViewsTranslationX?.times(fractionFromCenter)
                ?: defaultOffsetFromCenter.toFloat()

        start.translationX = endTranslation
        end.translationX = -endTranslation
        mid_start.translationX = midTranslation
        mid_end.translationX = -midTranslation
    }


    fun expandAt(expandedAt: Int) {
        this.expandedAt = expandedAt
        invalidate()
        requestLayout()
        expandTabs(1f)
    }

    fun collpaseAt(collapseAt: Int) {
        collapseTabs(1f)
    }

    public fun setIcons(@DrawableRes largeCenterIcon: Int = R.drawable.ic_ring,
                        @DrawableRes smallCenterIcon: Int = R.drawable.ic_view_white,
                        @DrawableRes startIcon: Int = R.drawable.ic_comment_white,
                        @DrawableRes endIcon: Int = R.drawable.ic_white_whatshot) {
        center.setImageResource(largeCenterIcon)
        bottom_center.setImageResource(smallCenterIcon)
        start.setImageResource(startIcon)
        end.setImageResource(endIcon)
    }

    private fun getDefaultIcons(): ArrayList<Int> {
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
            center.translationY = centerTranslationY * 4f
            bottom_center.translationY = centerTranslationY * 4f
        }


        val centerScale: Float = 1 - fractionFromCenter
        Log.e("onPageSelected", "position  $centerScale fractionFromCenter  $fractionFromCenter ")

        if (centerScale > 0.85f) {
            center.scaleX = centerScale
            center.scaleY = centerScale
        }
        bottom_center.alpha = centerScale
        bottom_center.scaleY = centerScale
        bottom_center.scaleX = centerScale
    }


    private fun setTabChangingColor(fractionFromCenter: Float) {
//        val color = mArgbEvaluator.evaluate(fractionFromCenter, mCenterColor, mSideColor) as Int
//
//        center.setColorFilter(color)
//        bottom_center.setColorFilter(color)
//        start.setColorFilter(color)
//        end.setColorFilter(color)

//        mIndicator.alpha = fractionFromCenter
//        mIndicator.scaleX = fractionFromCenter

        transitionBackground.alpha = fractionFromCenter

        moveViews(fractionFromCenter)
        moveAndScaleCenter(fractionFromCenter)
    }


    enum class Color(val value: Int) {
        RED(0),
        GREEN(1),
        BLUE(2)
    }
}
