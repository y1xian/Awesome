package com.yyxnb.awesome.popup

import android.content.Context
import android.view.View
import com.yyxnb.awesome.R
import com.yyxnb.view.popup.PopupUtils
import com.yyxnb.view.popup.code.BottomPopup

/**
 * 注册
 */
class RegisterBottomPopup(context: Context) : BottomPopup(context) {

    override fun initLayoutResId(): Int {
        return R.layout.popup_register_bottom_layout
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<View>(R.id.ivDel).setOnClickListener { v: View? -> dismiss() }
    }

    override fun getPopupHeight(): Int {
        return (PopupUtils.getWindowHeight(context) * .90f).toInt()
    }
}