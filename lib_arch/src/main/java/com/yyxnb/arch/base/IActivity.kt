package com.yyxnb.arch.base

import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.delegate.ActivityDelegate

interface IActivity : IView {

    fun getBaseDelegate(): ActivityDelegate?

    fun initWindows() {}

//    fun initArgs(extras: Bundle?): Boolean {
//        return true
//    }

    fun setSwipeBack(@SwipeStyle mSwipeBack: Int)
}