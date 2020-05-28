package com.yyxnb.arch.delegate

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.LruCache
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.utils.ActivityManagerUtils.killActivity
import com.yyxnb.arch.utils.ActivityManagerUtils.pushActivity

object ActivityLifecycle : ActivityLifecycleCallbacks {

    private val cache = LruCache<String, IActivityDelegate>(100)

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        pushActivity(activity)
        activity?.let { pushActivity(it) }

        if (activity !is IActivity) {
            return
        }

        val activityDelegate = fetchActivityDelegate(activity)
                ?: newDelegate(activity).apply { cache.put(getKey(activity), this) }

        activityDelegate.onCreate(savedInstanceState)

        registerFragmentCallback(activity)
    }

    private fun registerFragmentCallback(activity: Activity?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycle, true)
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        fetchActivityDelegate(activity)?.onStart()
    }

    override fun onActivityResumed(activity: Activity?) {
        fetchActivityDelegate(activity)?.onResume()
    }

    override fun onActivityPaused(activity: Activity?) {
        fetchActivityDelegate(activity)?.onPause()
    }

    override fun onActivityStopped(activity: Activity?) {
        fetchActivityDelegate(activity)?.onStop()
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let { killActivity(it) }
        fetchActivityDelegate(activity)?.onDestroy()
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        fetchActivityDelegate(activity)?.onSaveInstanceState(activity, outState)
    }

    private fun fetchActivityDelegate(activity: Activity?): IActivityDelegate? {
        if (activity !is IActivity) {
            return null
        }

        return cache.get(getKey(activity))
    }

    private fun newDelegate(activity: Activity): IActivityDelegate {
        return ActivityDelegateImpl(activity as FragmentActivity)
    }

    private fun getKey(activity: Activity): String = activity.javaClass.name + activity.hashCode()

}