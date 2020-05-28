package com.yyxnb.arch

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.yyxnb.common.AppConfig

/**
 * 监听应用状态
 */
class AppLifeObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        AppConfig.log("应用进入前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        AppConfig.log("应用进入后台")
    }
}