package com.fridayof1995.sample

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import com.fridayof1995.tabanimation.SnapTabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager);

    val mArgbEvaluator: ArgbEvaluator = ArgbEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerAdapter.addFragment(MainFragment.newInstance(0))
        viewPagerAdapter.addFragment(MainFragment.newInstance(1))
        viewPagerAdapter.addFragment(MainFragment.newInstance(2))
        viewPager.adapter = viewPagerAdapter

        val numTab = intent.getIntExtra("numOfTabs", 3)

        /**
         * Library methods and example usage.
         */
        tabLayout.numOfTabs = if (numTab.equals(SnapTabLayout.NumOfTabs.THREE.value)) {
            SnapTabLayout.NumOfTabs.THREE
        } else {
            viewPagerAdapter.addFragment(MainFragment.newInstance(3))
            viewPagerAdapter.addFragment(MainFragment.newInstance(4))
            SnapTabLayout.NumOfTabs.FIVE
        }

        tabLayout.setBackgroundCollapsed(R.drawable.tab_gradient_collapsed)
        tabLayout.setBackgroundExpanded(R.drawable.tab_gradient_expanded)

        tabLayout.smallCenterButton.setImageResource(R.drawable.ic_view_white)
        tabLayout.largeCenterButton.setImageResource(R.drawable.shadow_ring)
        tabLayout.startButton.setImageResource(R.drawable.ic_comment_white)
        tabLayout.endButton.setImageResource(R.drawable.ic_white_whatshot)
        tabLayout.midStart.setImageResource(R.drawable.ic_white_poll)
        tabLayout.midEnd.setImageResource(R.drawable.ic_white_email)

//        tabLayout.setTransitionIconColors(ContextCompat.getColor(this@MainActivity, android.R.color.white)
//                , ContextCompat.getColor(this@MainActivity, R.color.colorGrey))

        tabLayout.setCenterScale(0.85f)

        tabLayout.setIndicatorColor(ContextCompat.getColor(this@MainActivity, R.color.colorGrey))

        tabLayout.setVpTransitionBgColors(ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple)
                , ContextCompat.getColor(this@MainActivity, android.R.color.black)
                , ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark))

        tabLayout.smallCenterButton.setOnClickListener {
            toast("Bottom Center Clicked. Show some bottom sheet.")
        }
        tabLayout.setupWithViewPager(viewPager)
    }

}
