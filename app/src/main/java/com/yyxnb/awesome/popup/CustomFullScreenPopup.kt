package com.yyxnb.awesome.popup

import android.content.Context
import android.view.View
import com.yyxnb.awesome.R
import com.yyxnb.view.popup.code.FullScreenPopup

/**
 * 全屏
 */
class CustomFullScreenPopup(context: Context) : FullScreenPopup(context) {

    override fun initLayoutResId(): Int {
        return R.layout.popup_fullscreen_layout
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<View>(R.id.ivClose).setOnClickListener { v: View? -> dismiss() }
    }
}