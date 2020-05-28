package com.yyxnb.arch.base

import android.os.Bundle
import android.support.annotation.LayoutRes

interface IView {
    /**
     * 初始化布局
     */
    @LayoutRes
    fun initLayoutResId(): Int = 0

    /**
     * 初始化控制、监听等轻量级操作
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 处理重量级数据、逻辑
     */
    fun initViewData()

    /**
     * 初始化界面观察者的监听
     * 接收数据结果
     */
    fun initObservable() {}
    /**
     * 接收信息
     */
    //    void handleEvent(msg: MsgEvent?){}
}