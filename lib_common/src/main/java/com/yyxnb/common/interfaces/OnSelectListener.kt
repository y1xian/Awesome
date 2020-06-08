package com.yyxnb.common.interfaces

import android.view.View

/**
 * Description: 选择回调
 */
interface OnSelectListener {
    fun onClick(v: View?, position: Int, text: String?)
}