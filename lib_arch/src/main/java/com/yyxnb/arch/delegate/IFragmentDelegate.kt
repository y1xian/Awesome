package com.yyxnb.arch.delegate

import android.content.Context
import android.os.Bundle
import android.view.View

interface IFragmentDelegate {

    fun onAttached(context: Context?)

    fun onCreated(savedInstanceState: Bundle?)

    fun onViewCreated(view: View?, savedInstanceState: Bundle?)

    fun onActivityCreated(savedInstanceState: Bundle?)

    fun onStarted()

    fun onResumed()

    fun onPaused()

    fun onStopped()

    fun onSaveInstanceState(outState: Bundle?)

    fun onViewDestroyed()

    fun onDestroyed()

    fun onDetached()

    val isAdd: Boolean
}