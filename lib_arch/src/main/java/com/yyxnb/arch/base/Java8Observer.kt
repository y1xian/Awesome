package com.yyxnb.arch.base

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.util.Log
import java.io.Serializable

class Java8Observer(private val tag: String) : DefaultLifecycleObserver, Serializable {

    override fun onCreate(owner: LifecycleOwner) {
        Log.e(tag, owner.lifecycle.currentState.name)
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.w(tag, owner.lifecycle.currentState.name)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.e(tag, owner.lifecycle.currentState.name)
    }

}