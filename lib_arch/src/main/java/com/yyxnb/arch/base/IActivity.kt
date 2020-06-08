package com.yyxnb.arch.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.delegate.ActivityDelegate
import com.yyxnb.arch.utils.AppManager

interface IActivity : IView {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getBaseDelegate(): ActivityDelegate {
        var delegate: ActivityDelegate? = AppManager.activityDelegates?.get(hashCode())
        if (delegate == null) {
            delegate = ActivityDelegate(this)
            AppManager.activityDelegates?.put(hashCode(), delegate)
        }
        return delegate
    }

    fun initWindows() {}

    fun setSwipeBack(@SwipeStyle mSwipeBack: Int) {}
}