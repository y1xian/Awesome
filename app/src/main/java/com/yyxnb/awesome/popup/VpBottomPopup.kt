package com.yyxnb.awesome.popup

import android.content.Context
import android.view.View
import com.yyxnb.awesome.R
import com.yyxnb.awesome.fragments.VpMainFragment
import com.yyxnb.view.popup.PopupUtils
import com.yyxnb.view.popup.code.BottomPopup

/**
 * vp
 */
class VpBottomPopup(context: Context) : BottomPopup(context) {

    override fun initLayoutResId(): Int {
        return R.layout.popup_vp_bottom_layout
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<View>(R.id.ivDel).setOnClickListener { v: View? -> dismiss() }
        val ft = activity.supportFragmentManager.beginTransaction()
        ft.replace(R.id.mFrameLayout, VpMainFragment())
        ft.commitAllowingStateLoss()
    }

    override fun getPopupHeight(): Int {
        return (PopupUtils.getWindowHeight(context) * .70f).toInt()
    }
}