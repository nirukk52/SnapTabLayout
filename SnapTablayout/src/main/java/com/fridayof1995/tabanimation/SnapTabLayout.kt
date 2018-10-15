package com.fridayof1995.tabanimation

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.v4.view.ViewPager
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
    : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

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
    //var bottomCenter: ImageButton = (ImageButton(context))
    lateinit var bottomCenter: ImageButton


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
        bottomCenter = findViewById<ImageButton>(R.id.bottom_center)
    }


    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(position: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.e("onPageScrolled", "position $position positionOffset$positionOffset")

        if (position == expandedAt - 1) expandTabs(positionOffset, position) // expand
        else if (position == expandedAt) collapseTabs(positionOffset, position) // collapse

        if (numOfTabs == NumOfTabs.THREE) {
            if (position == expandedAt - 1) moveIndicatorToEnd(positionOffset, position) // expand
            else if (position == expandedAt) moveIndicatorToStart(positionOffset, position) // collapse
        } else {
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

        //   changeSelectedBackground(position)
    }


    private fun moveIndicatorToStart(positionOffset: Float, position: Int) {
        mIndicator.translationX =
                (positionOffset * (start.x + start.width - convertDpToPixel(6f, context)))
    }

    private fun moveIndicatorToEnd(positionOffset: Float, position: Int) {
        mIndicator.translationX =
                ((positionOffset - 1) * (start.x + start.width - convertDpToPixel(8f, context)))
    }


    private fun collapseTabs(positionOffset: Float, position: Int) {
        // collapses at position ==  1
        setTabChangingColor(positionOffset, position)
    }

    private fun expandTabs(positionOffset: Float, position: Int) {
        // expands at position == 0
        setTabChangingColor(1 - positionOffset, position)
    }

    fun setupWithViewPager(viewPager: ViewPager) {
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
        bottomCenter.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
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
                mCenterTranslationY = height - bottomCenter.bottom

                bottomCenter.viewTreeObserver.removeOnGlobalLayoutListener(this)
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

    private fun setSelectedBackground(@DrawableRes backgroundColor: Int) {
        start.setBackgroundResource(backgroundColor)
        mid_start.setBackgroundResource(backgroundColor)
        mid_end.setBackgroundResource(backgroundColor)
        end.setBackgroundResource(backgroundColor)
    }

    fun setMiddleIcons(@DrawableRes secondIconFromStart: Int = 0,
                       @DrawableRes secondIconFromEnd: Int = 0) {
        mid_start.setImageResource(secondIconFromStart)
        mid_end.setImageResource(secondIconFromEnd)
    }

    fun setCenterIcons(@DrawableRes largeCenterIcon: Int,
                       @Nullable @DrawableRes smallBottomCenterIcon: Int? = 0) {
        center.setImageResource(largeCenterIcon)
        if (smallBottomCenterIcon != null) bottomCenter.setImageResource(smallBottomCenterIcon)
        else bottomCenter.setImageResource(0)
    }

    fun setCornerIcons(@DrawableRes startIcon: Int,
                       @DrawableRes endIcon: Int) {
        start.setImageResource(startIcon)
        end.setImageResource(endIcon)
    }

    fun setBackground(@DrawableRes background: Int) {
        transitionBackground.setBackgroundResource(background)
    }

    private fun moveAndScaleCenter(fractionFromCenter: Float) {

        val centerTranslationY: Float? = mCenterTranslationY?.times(fractionFromCenter)

        if (centerTranslationY != null) {
            center.translationY = centerTranslationY * 4f
            bottomCenter.translationY = centerTranslationY * 4f
        }

        val centerScale: Float = 1 - fractionFromCenter

        if (centerScale > 0.85f) {
            center.scaleX = centerScale
            center.scaleY = centerScale
        }
        bottomCenter.alpha = centerScale
        bottomCenter.scaleY = centerScale
        bottomCenter.scaleX = centerScale
    }


    private fun setTabChangingColor(fractionFromCenter: Float, position: Int) {
//        val color = mArgbEvaluator.evaluate(fractionFromCenter, mCenterColor, mSideColor) as Int
//       center.setColorFilter(color)
//       bottomCenter.setColorFilter(color)
//       start.setColorFilter(color)
//       end.setColorFilter(color)

        mIndicator.alpha = fractionFromCenter
        mIndicator.scaleX = fractionFromCenter

        transitionBackground.alpha = fractionFromCenter


        moveViews(fractionFromCenter)
        moveAndScaleCenter(fractionFromCenter)
    }

    //TODO Change background of tab on selection.
    private fun changeSelectedBackground(position: Int) {

        when (position) {
            0 -> {
                start.background.alpha = 255
                mid_start.background.alpha = 0
                mid_end.background.alpha = 0
                end.background.alpha = 0
            }
            1 -> {
                start.background.alpha = 0
                mid_start.background.alpha = 255
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
                mid_end.background.alpha = 255
                end.background.alpha = 0
            }
            4 -> {
                start.background.alpha = 0
                mid_start.background.alpha = 0
                mid_end.background.alpha = 0
                end.background.alpha = 255
            }
        }
    }

    enum class NumOfTabs(val value: Int) {
        THREE(3),
        FIVE(5)
    }
}
