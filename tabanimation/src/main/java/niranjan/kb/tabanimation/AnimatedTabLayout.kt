package niranjan.kb.tabanimation

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.Resources
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
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {


    val mArgbEvaluator: ArgbEvaluator = ArgbEvaluator()
    val mCenterColor: Int = ContextCompat.getColor(context, android.R.color.white)
    val mSideColor: Int = ContextCompat.getColor(context, android.R.color.black)

    val centerPadding: Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
            , 40f, resources.displayMetrics).toInt()
    // val centerPadding: Int = dpToPx(40f)
    var mEndViewsTranslationX: Int? = null
    var mCenterTranslationY: Int? = null

    var tabNumber: Int = 1

    init {
        init()
    }


    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.smart_tabs_view, this, true)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.e("onPageScrolled", "position $position positionOffset$positionOffset")
        if (position == 1) {
            setTabChangingColor(positionOffset)
            mIndicator.translationX = (positionOffset * centerPadding * 2f)
        } else if (position == 0) {
            setTabChangingColor(1 - positionOffset)
            mIndicator.translationX = ((positionOffset - 1) * centerPadding * 2f)
        }
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(this)
        bottom_center.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mEndViewsTranslationX = (bottom_center.x - start.x - centerPadding).toInt()
                mCenterTranslationY = height - bottom_center.bottom
                bottom_center.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                viewPager.currentItem = 1
            }
        })
    }

    fun setExpandedAt(tabNumber: Int = 1) {
        this.tabNumber = tabNumber
    }

    fun getExpandedAt(): Int {
        return this.tabNumber
    }

    override fun onPageSelected(i: Int) {

    }

    override fun onPageScrollStateChanged(i: Int) {

    }


    private fun moveAndScaleCenter(fractionFromCenter: Float) {

//        val centerScale: Float = 1f + (fractionFromCenter * 0.8f)
//
//
//
//        center.scaleX = centerScale
//        center.scaleY = centerScale

        val centerTranslationY: Float? = mCenterTranslationY?.times(fractionFromCenter)

        if (centerTranslationY != null) {
            center.translationY = centerTranslationY * 4
            bottom_center.translationY = centerTranslationY * 4
        }

        bottom_center.alpha = 1 - fractionFromCenter
        bottom_center.scaleY = 1 - fractionFromCenter
        bottom_center.scaleX = 1 - fractionFromCenter
    }

    private fun moveViews(fractionFromCenter: Float) {

        val translation: Float = mEndViewsTranslationX?.times(fractionFromCenter)
                ?: centerPadding.toFloat()

        start.translationX = translation
        end.translationX = -translation
    }

    private fun setTabChangingColor(fractionFromCenter: Float) {
        val color = mArgbEvaluator.evaluate(fractionFromCenter, mCenterColor, mSideColor) as Int

        center.setColorFilter(color)
        bottom_center.setColorFilter(color)
        start.setColorFilter(color)
        end.setColorFilter(color)

        mIndicator.alpha = fractionFromCenter
        mIndicator.scaleX = fractionFromCenter

        moveViews(fractionFromCenter)
        moveAndScaleCenter(fractionFromCenter)
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

}
