package com.fridayof1995.tabanimation

import android.animation.ArgbEvaluator
import android.animation.FloatEvaluator
import android.content.Context
import androidx.annotation.ColorInt
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageButton
import kotlinx.android.synthetic.main.snap_tab_view.view.*


/**
 * Created by Depression on 11-08-2018.
 */
class SnapTabLayout
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), androidx.viewpager.widget.ViewPager.OnPageChangeListener {


    val mArgbEvaluator: ArgbEvaluator = ArgbEvaluator()
    var mCenterColor: Int = 0
    var mSideColor: Int = 0
    var mCenterScale: Float = 0.80f

    private var defaultOffsetFromCenter: Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
            , 80f, resources.displayMetrics).toInt()

    private var expandedAt: Int = 0
    var numOfTabs: NumOfTabs = NumOfTabs.FIVE
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    private var mEndViewsTranslationX: Int? = null
    private var mMidViewsTranslationX: Int? = null
    private var mCenterTranslationY: Int? = null

    lateinit var smallCenterButton: ImageButton
    lateinit var largeCenterButton: ImageButton
    lateinit var startButton: ImageButton
    lateinit var endButton: ImageButton
    lateinit var midStart: ImageButton
    lateinit var midEnd: ImageButton
    lateinit var vpager: androidx.viewpager.widget.ViewPager

    var mBgRight: Int = 0
    var mBgLeft: Int = 0
    var mBgCenter: Int = 0

    init {
        init()
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.VPAnimatedTabLayout,
                0, 0)

        try {
            val anumOfTabs: Int = a.getInt(R.styleable.VPAnimatedTabLayout_numOfTabs
                    , 3)
            if (anumOfTabs == 3) {
                numOfTabs = NumOfTabs.THREE
                mid_start.visibility = View.GONE
                mid_end.visibility = View.GONE
                expandedAt = 1
            } else {
                numOfTabs = NumOfTabs.FIVE
                mid_start.visibility = View.VISIBLE
                mid_end.visibility = View.VISIBLE
                expandedAt = 2
            }
        } finally {
            a.recycle()
        }
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.snap_tab_view, this, true)
        smallCenterButton = findViewById<ImageButton>(R.id.bottom_center)
        largeCenterButton = findViewById<ImageButton>(R.id.center)
        startButton = findViewById<ImageButton>(R.id.start)
        endButton = findViewById<ImageButton>(R.id.end)
        midStart = findViewById<ImageButton>(R.id.mid_start)
        midEnd = findViewById<ImageButton>(R.id.mid_end)
    }

    override fun onPageScrollStateChanged(state: Int) {

        //if(mCenterMovePosition = state)
    }

    override fun onPageSelected(position: Int) {
        Log.e("onPageSelected", "position   $position")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position == expandedAt - 1) expandTabs(positionOffset, position) // expand
        else if (position == expandedAt) collapseTabs(positionOffset, position) // collapse

        if (numOfTabs == NumOfTabs.THREE) {
            moveIndicatorWithThreeTabs(positionOffset, position)
        } else {
            moveIndicatorWithFiveTabs(position)
        }
        //   changeSelectedBackground(position)
    }

    private fun moveIndicatorWithThreeTabs(positionOffset: Float, position: Int) {
        if (position == expandedAt - 1) moveIndicatorToEnd(positionOffset) // expand
        else if (position == expandedAt) moveIndicatorToStart(positionOffset) // collapse
    }

    private fun moveIndicatorWithFiveTabs(position: Int) {

        if (position < expandedAt) when (position) {
            0 -> {
                mIndicator.x =
                        start.x + (start.width / 8)
            }
            1 -> {
                mIndicator.x =
                        mid_start.x + (mid_start.width / 8)
            }
        }
        else if (position > expandedAt) when (position) {
            3 -> {
                mIndicator.x =
                        mid_end.x + (mid_end.width / 8)
            }
            4 -> {
                mIndicator.x =
                        end.x + (end.width / 8)

            }
        }
        else if (position == expandedAt) {
            mIndicator.x =
                    mid_end.x + (mid_end.width / 8)
        }
    }

    private fun moveIndicatorToStart(positionOffset: Float) {
        mIndicator.translationX =
                (positionOffset * (start.x + start.width - convertDpToPixel(6f, context)))
    }

    private fun moveIndicatorToEnd(positionOffset: Float) {
        mIndicator.translationX =
                ((positionOffset - 1) * (start.x + start.width - convertDpToPixel(8f, context)))
    }

    private fun collapseTabs(positionOffset: Float, position: Int) {
        // collapses at position ==  1
        setTabChangingColor(positionOffset, position)
        //changeSelectedBackground(position,positionOffset)
    }

    private fun expandTabs(positionOffset: Float, position: Int) {
        // expands at position == 0
        setTabChangingColor(1 - positionOffset, position)
        //changeSelectedBackground(position,1 - positionOffset)
    }

    /**
     * Hide extra buttons if using three tabs.
     * Sets onClickListener on each view.
     * Sets defaultOffset from center while collapsing.
     */
    fun setupWithViewPager(viewPager: androidx.viewpager.widget.ViewPager) {
        vpager = viewPager
        if (numOfTabs == NumOfTabs.THREE) {
            mid_start.visibility = View.GONE
            mid_end.visibility = View.GONE
            expandedAt = 1
        } else {
            mid_start.visibility = View.VISIBLE
            mid_end.visibility = View.VISIBLE
            expandedAt = 2
        }
        viewPager.addOnPageChangeListener(this)
        viewPager.setPageTransformer(true, StackTransformer())
        smallCenterButton.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                defaultOffsetFromCenter = (center.width / 2)
                if (numOfTabs == NumOfTabs.FIVE) {
                    if (expandedAt < 2) {
                        mMidViewsTranslationX = (center.x - mid_start.x + defaultOffsetFromCenter - 40).toInt()
                        mEndViewsTranslationX = (center.x - start.x - mid_start.width + defaultOffsetFromCenter - 40).toInt()
                    } else {
                        mMidViewsTranslationX = (center.x - mid_start.x + defaultOffsetFromCenter - 40 * 6).toInt()
                        mEndViewsTranslationX = (center.x - start.x - mid_start.width + defaultOffsetFromCenter - 40 * 6).toInt()
                    }
                } else {
                    mMidViewsTranslationX = (center.x - mid_start.x - defaultOffsetFromCenter + 40).toInt()
                    mEndViewsTranslationX = (center.x - start.x - mid_start.width - defaultOffsetFromCenter + 40).toInt()
                }
                mCenterTranslationY = height - smallCenterButton.bottom

                smallCenterButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
                viewPager.currentItem = expandedAt
            }
        })

        center.setOnClickListener {
            if (viewPager.currentItem != expandedAt) {
                viewPager.currentItem = expandedAt
            }
        }
        start.setOnClickListener {
            if (viewPager.currentItem != 0) {
                viewPager.currentItem = 0
            }
        }
        end.setOnClickListener {
            if (viewPager.currentItem != numOfTabs.value - 1) {
                viewPager.currentItem = numOfTabs.value - 1
            }
            if (numOfTabs == NumOfTabs.FIVE) {
                mIndicator.x =
                        end.x + (end.width / 8)
            }
        }
        mid_start.setOnClickListener {
            if (viewPager.currentItem != expandedAt - 1) {
                viewPager.currentItem = expandedAt - 1
            }
        }
        mid_end.setOnClickListener {
            if (viewPager.currentItem != expandedAt + 1) {
                viewPager.currentItem = expandedAt + 1
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

    private fun moveAndScaleCenter(fractionFromCenter: Float) {

        val centerTranslationY: Float? = mCenterTranslationY?.times(fractionFromCenter)

        if (centerTranslationY != null) {
            center.translationY = centerTranslationY * 4f
            smallCenterButton.translationY = centerTranslationY * 4f
        }

        val centerScale: Float = 1 - fractionFromCenter
        val mScaleEvaluator = FloatEvaluator()
        val scale = mScaleEvaluator.evaluate(fractionFromCenter, 1, mCenterScale)
        center.scaleX = scale
        center.scaleY = scale

        smallCenterButton.alpha = centerScale
        smallCenterButton.scaleY = centerScale
        smallCenterButton.scaleX = centerScale
    }

    /**
     * Transitions icon colors.
     * Transitions ViewPager background color.
     * Transitions SnapTablayout background.
     */
    private fun setTabChangingColor(fractionFromCenter: Float, position: Int) {

        val color = mArgbEvaluator.evaluate(fractionFromCenter, mCenterColor, mSideColor) as Int
        center.setColorFilter(color)
        bottom_center.setColorFilter(color)
        start.setColorFilter(color)
        end.setColorFilter(color)
        mid_end.setColorFilter(color)
        mid_start.setColorFilter(color)

        if (position < expandedAt) {

            val colorToLeft = mArgbEvaluator.evaluate(fractionFromCenter, mBgCenter, mBgLeft) as Int
            vpager.setBackgroundColor(colorToLeft)

        } else if (position == expandedAt) {

            val colorToRight = mArgbEvaluator.evaluate(fractionFromCenter, mBgCenter, mBgRight) as Int
            vpager.setBackgroundColor(colorToRight)

        }
        mIndicator.alpha = fractionFromCenter
        mIndicator.scaleX = fractionFromCenter

        transitionBackground.alpha = fractionFromCenter
        transitionBackground2.alpha = 1 - fractionFromCenter

        moveViews(fractionFromCenter)
        moveAndScaleCenter(fractionFromCenter)

    }

    // Public Methods
    fun setBackgroundCollapsed(background: Int) {
        transitionBackground.setBackgroundResource(background)
    }

    fun setBackgroundExpanded(background: Int) {
        transitionBackground2.setBackgroundResource(background)
    }

    fun setVpTransitionBgColors(@ColorInt leftSideColor: Int, @ColorInt centerColor: Int, @ColorInt rightSideColor: Int) {
        mBgLeft = leftSideColor
        mBgCenter = centerColor
        mBgRight = rightSideColor
    }

    fun setTransitionIconColors(@ColorInt centerColor: Int, @ColorInt sideColor: Int) {
        mCenterColor = centerColor
        mSideColor = sideColor
    }

    fun setIndicatorColor(@ColorInt colorInt: Int) {
        mIndicator.setColorFilter(colorInt)
    }

    fun setCenterScale(scaleDownCenterIcon: Float) {
        mCenterScale = scaleDownCenterIcon
    }


    //TODO Change background of tab on selection.
    private fun changeSelectedBackground(position: Int, fractionFromCenter: Float) {
        val alphaValue = mArgbEvaluator.evaluate(fractionFromCenter, 0, 255) as Int
        //  Log.e("background", "position $position fractionFromCenter $fractionFromCenter  alphaValue$alphaValue ")
        when (position) {
            0 -> {
                start.background.alpha = alphaValue
                mid_start.background.alpha = 0
                mid_end.background.alpha = 0
                end.background.alpha = 0
            }
            1 -> {
                start.background.alpha = alphaValue
                mid_start.background.alpha = alphaValue
                mid_end.background.alpha = 0
                end.background.alpha = 0
            }
            2 -> {
                start.background.alpha = 0
                mid_start.background.alpha = 0
                mid_end.background.alpha = 0
                end.background.alpha = 0
            }
            3 -> {
                start.background.alpha = 0
                mid_start.background.alpha = 0
                mid_end.background.alpha = alphaValue
                end.background.alpha = 0
            }
            4 -> {
                start.background.alpha = 0
                mid_start.background.alpha = 0
                mid_end.background.alpha = 0
                end.background.alpha = alphaValue
            }
        }
    }

    enum class NumOfTabs(val value: Int) {
        THREE(3),
        FIVE(5)
    }
}
