package com.yyxnb.awesome.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

class BaseFragmentPagerAdapter(
        fragmentManager: FragmentManager?,
        fragments: List<Fragment>?,
        titles: List<String>?
) : FragmentPagerAdapter(fragmentManager) {

    private val mFragments: MutableList<Fragment> = ArrayList()
    private val mTitles: MutableList<String> = ArrayList()

    override fun getItem(i: Int): Fragment {
        return mFragments[i]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position < mTitles.size) mTitles[position] else null
    }

    init {
        if (fragments != null) {
            mFragments.addAll(fragments)
        }
        if (titles != null) {
            mTitles.addAll(titles)
        }
    }
}