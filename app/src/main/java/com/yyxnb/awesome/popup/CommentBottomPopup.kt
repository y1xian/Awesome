package com.yyxnb.awesome.popup

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.yyxnb.adapter.ItemDecoration
import com.yyxnb.awesome.R
import com.yyxnb.awesome.adapter.StringListAdapter
import com.yyxnb.awesome.data.DataConfig.dialogList
import com.yyxnb.view.popup.PopupUtils
import com.yyxnb.view.popup.code.BottomPopup
import kotlinx.android.synthetic.main.popup_comment_bottom_layout.view.*

/**
 * 评论
 */
class CommentBottomPopup(context: Context) : BottomPopup(context) {

    private val mAdapter by lazy { StringListAdapter() }

    override fun initLayoutResId(): Int {
        return R.layout.popup_comment_bottom_layout
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<View>(R.id.ivDel).setOnClickListener { v: View? -> dismiss() }
        val decoration = ItemDecoration(context)
        decoration.isOnlySetItemOffsetsButNoDraw = true
        decoration.isDrawBorderTopAndBottom = true
        decoration.isDrawBorderLeftAndRight = true
        decoration.setDividerHeight(10)
        decoration.setDividerWidth(20)
        mRecyclerView.setLayoutManager(LinearLayoutManager(context))
        mRecyclerView.addItemDecoration(decoration)
        mRecyclerView.setAdapter(mAdapter)
        mAdapter.setDataItems(dialogList)
    }

    override fun getPopupHeight(): Int {
        return (PopupUtils.getWindowHeight(context) * .70f).toInt()
    }
}