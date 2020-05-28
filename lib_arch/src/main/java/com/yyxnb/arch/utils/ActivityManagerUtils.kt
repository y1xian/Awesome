package com.yyxnb.arch.utils

import android.app.Activity
import java.io.Serializable
import java.util.*

/**
 * 管理所有activity
 */
object ActivityManagerUtils : Serializable {

    @Volatile
    private var activityStack: Stack<Activity>? = null

    fun count(): Int {
        return activityStack!!.size
    }

    val activityList: List<Activity?>
        get() {
            val list: MutableList<Activity?> = ArrayList()
            if (!activityStack!!.isEmpty()) {
                list.addAll(activityStack!!)
            }
            return list
        }

    fun pushActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        activityStack!!.add(activity)
    }

    fun killActivity(activity: Activity?) {
        if (activityStack != null) {
//            activity.finish();
            activityStack!!.remove(activity)
        }
    }

    fun currentActivity(): Activity? {
        return if (activityStack!!.isEmpty()) {
            null
        } else activityStack!!.lastElement()
    }

    fun beforeActivity(): Activity? {
        return if (count() < 2) {
            null
        } else activityStack!![count() - 2]
    }

}