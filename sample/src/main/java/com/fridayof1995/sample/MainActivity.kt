package com.fridayof1995.sample

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.fridayof1995.tabanimation.SnapTabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager);

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
        tabLayout.setBackground(R.drawable.tab_gradient)
        tabLayout.smallCenterButton.setOnClickListener {
            toast("Bottom Center Clicked. Show some bottom sheet.")
        }
        tabLayout.setupWithViewPager(viewPager)


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                if (position == 0) {
//                    val purple: Int = ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple)
//
//                    transitionBackground.setBackgroundColor(
//                            ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple))
//                    transitionBackground.alpha = 1 - positionOffset
//
//                } else if (position == 1) {
//                    val orange: Int = ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark)
//
//                    transitionBackground.setBackgroundColor(
//                            ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark))
//                    transitionBackground.alpha = positionOffset
//                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

}
