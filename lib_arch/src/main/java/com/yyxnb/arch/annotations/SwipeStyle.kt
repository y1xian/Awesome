package com.yyxnb.arch.annotations

import android.support.annotation.IntDef

@IntDef(SwipeStyle.Full, SwipeStyle.Edge, SwipeStyle.None)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class SwipeStyle {
    companion object {
        // 全屏
        const val Full = 1

        // 边缘
        const val Edge = 2

        // none
        const val None = 0
    }
}