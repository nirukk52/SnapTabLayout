package com.fridayof1995.sample

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerAdapter.addFragment(MainFragment.newInstance(0))
        viewPagerAdapter.addFragment(MainFragment.newInstance(1))
        viewPagerAdapter.addFragment(MainFragment.newInstance(2))
        viewPagerAdapter.addFragment(MainFragment.newInstance(3))
        viewPagerAdapter.addFragment(MainFragment.newInstance(4))



        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

//        moveToStart.setOnClickListener { tabLayout.setTabChangingColor(1f) }
//
//        moveToCenter.setOnClickListener { tabLayout.setTabChangingColor(0f) }
//
//        moveToEnd.setOnClickListener { tabLayout.setTabChangingColor(1f) }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    val purple: Int = ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple)

                    transitionBackground.setBackgroundColor(
                            ContextCompat.getColor(this@MainActivity, android.R.color.holo_purple))
                    transitionBackground.alpha = 1 - positionOffset

                } else if (position == 1) {
                    val orange: Int = ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark)

//            transitionBackground.background = ColorDrawable(ContextCompat
//                    .getColor(context, android.R.color.holo_purple))
//            transitionBackground.background.alpha = (1 - positionOffset).toInt()

                    transitionBackground.setBackgroundColor(
                            ContextCompat.getColor(this@MainActivity, android.R.color.holo_orange_dark))
                    transitionBackground.alpha = positionOffset
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}
