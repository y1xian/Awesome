package com.yyxnb.awesome.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.awesome.R
import com.yyxnb.awesome.bean.TikTokBean

class NetWorkListAdapter : BaseAdapter<TikTokBean>(R.layout.item_test_list_layout) {

    override fun bind(holder: BaseViewHolder, bean: TikTokBean, position: Int) {
        holder.setText(R.id.tvText, " --- 第 " + bean.id + " 条 ------- " + bean.title)
        addChildClickViewIds(holder, R.id.btnAdd)
        addChildLongClickViewIds(holder, R.id.btnAdd)
    }

}