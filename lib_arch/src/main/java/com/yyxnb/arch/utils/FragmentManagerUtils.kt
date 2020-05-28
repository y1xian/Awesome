package com.yyxnb.arch.utils

import android.support.v4.app.Fragment
import java.io.Serializable
import java.util.*

/**
 * 管理所有fragment
 */
object FragmentManagerUtils : Serializable {

    @Volatile
    private var fragmentStack: Stack<Fragment>? = null

    fun count(): Int {
        return fragmentStack!!.size
    }

    val fragmentList: List<Fragment?>
        get() {
            val list: MutableList<Fragment?> = ArrayList()
            if (!fragmentStack!!.isEmpty()) {
                list.addAll(fragmentStack!!)
            }
            return list
        }

    fun pushFragment(fragment: Fragment?) {
        if (fragmentStack == null) {
            fragmentStack = Stack<Fragment>()
        }
        fragmentStack!!.add(fragment)
    }

    fun killFragment(fragment: Fragment?) {
        if (fragmentStack != null) {
            fragmentStack!!.remove(fragment)
        }
    }

    fun currentFragment(): Fragment? {
        return if (fragmentStack!!.isEmpty()) {
            null
        } else fragmentStack!!.lastElement()
    }

    fun beforeFragment(): Fragment? {
        return if (count() < 2) {
            null
        } else fragmentStack!![count() - 2]
    }

}