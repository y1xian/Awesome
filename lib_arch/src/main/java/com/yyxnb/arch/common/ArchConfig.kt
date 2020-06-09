package com.yyxnb.arch.common

import android.graphics.Color
import com.yyxnb.arch.R
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.common.AppConfig
import java.io.Serializable

object ArchConfig : Serializable {

    const val FRAGMENT = "FRAGMENT"
    const val BUNDLE = "BUNDLE"
    const val REQUEST_CODE = "REQUEST_CODE"
    const val MSG_EVENT = "MSG_EVENT"

    const val NEED_LOGIN_CODE = -100

    /**
     * 侧滑
     */
    var swipeBack = SwipeStyle.Edge

    /**
     * 状态栏透明
     */
    var statusBarTranslucent = true

    /**
     * 给系统窗口留出空间
     */
    var fitsSystemWindows = false

    /**
     * 状态栏文字颜色
     */
    var statusBarStyle = BarStyle.DarkContent

    /**
     * 状态栏颜色
     */
    var statusBarColor = AppConfig.getInstance().context.resources.getColor(R.color.statusBar)

    /**
     * 如果状态栏处于白色且状态栏文字也处于白色，避免看不见
     */
    var shouldAdjustForWhiteStatusBar = Color.parseColor("#4A4A4A")

    /**
     * 虚拟键背景颜色
     */
    var navigationBarColor = Color.TRANSPARENT

    /**
     * 虚拟键颜色
     */
    var navigationBarStyle = BarStyle.DarkContent

    /**
     * 登录状态
     */
    var needLogin = false
}