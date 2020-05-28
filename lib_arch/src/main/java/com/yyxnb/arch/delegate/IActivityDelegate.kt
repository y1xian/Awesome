package com.yyxnb.arch.delegate

import android.app.Activity
import android.os.Bundle

interface IActivityDelegate {

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(activity: Activity?, outState: Bundle?)

    fun onDestroy()
}