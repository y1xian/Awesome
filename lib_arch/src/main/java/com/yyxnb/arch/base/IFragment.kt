package com.yyxnb.arch.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.yyxnb.arch.delegate.FragmentDelegate
import com.yyxnb.arch.utils.AppManager

interface IFragment : IView {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getBaseDelegate(): FragmentDelegate {
        var delegate: FragmentDelegate? = AppManager.fragmentDelegates?.get(hashCode())
        if (delegate == null) {
            delegate = FragmentDelegate(this)
            AppManager.fragmentDelegates?.put(hashCode(), delegate)
        }
        return delegate
    }

    /**
     * 用户可见时候调用
     */
    fun onVisible() {}

    /**
     * 用户不可见时候调用
     */
    fun onInVisible() {}

    fun initArguments(): Bundle? {
        return null
    }

    fun sceneId(): String? {
        return null
    }
}