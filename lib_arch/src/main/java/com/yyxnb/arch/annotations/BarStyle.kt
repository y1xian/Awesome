package com.yyxnb.arch.annotations

import android.support.annotation.IntDef

@IntDef(BarStyle.DarkContent, BarStyle.LightContent, BarStyle.None)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class BarStyle {
    companion object {
        // 深色
        const val DarkContent = 1

        // 浅色
        const val LightContent = 2

        // none
        const val None = 0
    }
}