package com.fridayof1995.sample

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.*

/**
 * Created by Depression on 10-08-2018.
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        if(position == 1){

        }
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
        notifyDataSetChanged()
    }

    fun addFragmentAt(fragment: Fragment, position: Int) {
        mFragmentList.add(position, fragment)
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = "ViewPagerAdapter"
    }
}
